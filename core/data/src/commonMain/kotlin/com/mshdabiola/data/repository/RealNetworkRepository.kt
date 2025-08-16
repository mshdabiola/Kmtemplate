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

import com.mshdabiola.model.Platform
import com.mshdabiola.model.ReleaseInfo
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
     * Returns ReleaseInfo.Success with release metadata and the matching APK asset URL when:
     * - running on Android,
     * - a release asset matching the current platform flavor/build type exists,
     * - both the current and online versions parse successfully,
     * - pre-release versions are allowed (or the online release is not a pre-release),
     * - and the online version is strictly newer than the current version.
     *
     * Otherwise returns ReleaseInfo.Error with a short message describing the failure:
     * - "Device not supported" if not running on Android,
     * - "Asset not found" if no matching APK asset is present,
     * - "Invalid version format" if either version string cannot be parsed,
     * - "Pre-release versions are not allowed" if a pre-release is encountered while disallowed,
     * - "Current version is greater than latest version" or "Current version is equal to latest version" for non-upgrade cases,
     * - or a generic "Unknown error" when an unexpected exception occurs.
     *
     * @param currentVersion the currently installed app version string to compare against the latest release tag.
     * @param allowPreRelease if false, pre-release online versions will be ignored and treated as an error.
     * @return ReleaseInfo.Success when an upgradeable release is found; otherwise ReleaseInfo.Error with a brief reason.
     */
    override suspend fun getLatestReleaseInfo(currentVersion: String, allowPreRelease: Boolean): ReleaseInfo {
        if (platform !is Platform.Android) {
            return ReleaseInfo.Error("Device not supported")
        }

        val name = "app-${platform.flavor.id}-${platform.buildType.id}-unsigned-signed.apk"

        return try {
            val gitHubReleaseInfo = networkSource.getLatestKmtemplateRelease()
            val asset = gitHubReleaseInfo
                .assets
                ?.firstOrNull {
                    it?.browserDownloadUrl?.contains(name) ?: false
                }

            val currentParsedVersion = ParsedVersion.fromString(currentVersion)
            val onlineParsedVersion = ParsedVersion.fromString(gitHubReleaseInfo.tagName ?: "")

            when {
                asset == null ->
                    throw Exception("Asset not found")
                onlineParsedVersion == null || currentParsedVersion == null ->
                    throw Exception("Invalid version format")
                !allowPreRelease && gitHubReleaseInfo.prerelease==true ->
                    throw Exception("Pre-release versions are not allowed")
                currentParsedVersion > onlineParsedVersion ->
                    throw Exception("Current version is greater than latest version")
                currentParsedVersion == onlineParsedVersion ->
                    throw Exception("Current version is equal to latest version")
            }

            ReleaseInfo.Success(
                tagName = gitHubReleaseInfo.tagName ?: "",
                releaseName = gitHubReleaseInfo.releaseName ?: "",
                body = gitHubReleaseInfo.body ?: "",
                asset = asset.browserDownloadUrl ?: "",
            )
        } catch (e: Exception) {
            ReleaseInfo.Error(e.message ?: "Unknown error")
        }
    }
}
