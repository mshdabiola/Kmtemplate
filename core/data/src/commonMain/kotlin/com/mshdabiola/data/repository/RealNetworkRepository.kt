/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.data.repository

import com.mshdabiola.model.AssetNotFoundException
import com.mshdabiola.model.InvalidVersionFormatException
import com.mshdabiola.model.NoUpdateAvailableException
import com.mshdabiola.model.Platform
import com.mshdabiola.model.PreReleaseNotAllowedException
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.model.UpdateException
import com.mshdabiola.network.NetworkDataSource

internal class RealNetworkRepository(
    private val networkSource: NetworkDataSource,
    private val platform: Platform,
) : NetworkRepository {
    /**
     * Placeholder implementation that would navigate to Google.
     *
     * Currently a no-op that returns an empty string; future implementations should perform the network/navigation call (e.g., via `networkSource`) and return the resulting URL or identifier.
     *
     * @return An empty string in the current implementation. Future implementations should return the Google navigation result (e.g., a URL).
     */
    override suspend fun gotoGoogle(): String {
        return "" // Placeholder, actual implementation would call networkSource
    }

    /**
     * Fetches the latest release information for the app and compares it to the provided current version.
     *
     * Returns [ReleaseInfo.NewUpdate] with release metadata and the matching APK asset URL when:
     * - running on Android,
     * - a release asset matching the current platform flavor/build type exists,
     * - both the current and online versions parse successfully,
     * - pre-release versions are allowed (or the online release is not a pre-release),
     * - and the online version is strictly newer than the current version.
     * Returns [ReleaseInfo.UpToDate] if the current version is the same as the latest online version
     * (and all other conditions for a valid release are met, e.g., asset found, versions parseable).
     *
     * Otherwise returns [ReleaseInfo.Error] containing an exception that describes the failure:
     * - [UpdateException] ("Device not supported") if not running on Android.
     * - [AssetNotFoundException] ("Asset not found") if no matching APK asset is present.
     * - [InvalidVersionFormatException] ("Invalid version format") if either version string cannot be parsed.
     * - [PreReleaseNotAllowedException] ("Pre-release versions are not allowed") if a pre-release is encountered while disallowed.
     * - [NoUpdateAvailableException] ("Current version is greater than latest version") if the current version is newer.
     * - Or a generic [Exception] for other unexpected issues, wrapped in [ReleaseInfo.Error].
     *
     * @param currentVersion the currently installed app version string to compare against the latest release tag.
     * @param allowPreRelease if false, pre-release online versions will be ignored and treated as an error.
     * @return A [ReleaseInfo] sealed type: [ReleaseInfo.NewUpdate], [ReleaseInfo.UpToDate], or [ReleaseInfo.Error].
     */
    override suspend fun getLatestReleaseInfo(currentVersion: String, allowPreRelease: Boolean): ReleaseInfo {
        if (platform !is Platform.Android) {
            // Explicitly return Error with UpdateException for non-Android platform
            return ReleaseInfo.Error(UpdateException("Device not supported"))
        }

        val name = "app-${platform.flavor.id}-${platform.buildType.id}-unsigned-signed.apk"

        return try {
            val gitHubReleaseInfo = networkSource.getLatestKmtemplateRelease()
            val asset = gitHubReleaseInfo
                .assets
                ?.firstOrNull {
                    it?.browserDownloadUrl?.contains(name) ?: false
                }

            // Perform mandatory checks that throw specific UpdateExceptions
            if (asset == null) {
                throw AssetNotFoundException("Asset not found")
            }

            val currentParsedVersion = ParsedVersion.fromString(currentVersion)
            val onlineParsedVersion = ParsedVersion.fromString(gitHubReleaseInfo.tagName ?: "")

            if (onlineParsedVersion == null || currentParsedVersion == null) {
                throw InvalidVersionFormatException("Invalid version format")
            }
            if (!allowPreRelease && gitHubReleaseInfo.prerelease == true) {
                throw PreReleaseNotAllowedException("Pre-release versions are not allowed")
            }
            if (currentParsedVersion > onlineParsedVersion) {
                throw NoUpdateAvailableException("Current version is greater than latest version")
            }

            // If all checks pass, determine if it's an update or up-to-date
            if (currentParsedVersion == onlineParsedVersion) {
                ReleaseInfo.UpToDate
            } else { // Implies currentParsedVersion < onlineParsedVersion
                ReleaseInfo.NewUpdate(
                    tagName = gitHubReleaseInfo.tagName ?: "",
                    releaseName = gitHubReleaseInfo.releaseName ?: "",
                    body = gitHubReleaseInfo.body ?: "",
                    asset = asset.browserDownloadUrl ?: "", // asset is non-null here
                )
            }
        } catch (e: UpdateException) { // Catch specific UpdateExceptions from checks
            ReleaseInfo.Error(e)
        } catch (e: Exception) { // Catch any other unexpected exceptions
            ReleaseInfo.Error(e) // Wrap in ReleaseInfo.Error
        }
    }
}
