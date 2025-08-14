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
    override suspend fun gotoGoogle(): String {
        return "" // Placeholder, actual implementation would call networkSource
    }

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
                allowPreRelease && onlineParsedVersion.preReleaseType != null ->
                    throw Exception("Pre-release versions are not allowed")
                currentParsedVersion > onlineParsedVersion ->
                    throw Exception("Current version is greater than latest version")
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
