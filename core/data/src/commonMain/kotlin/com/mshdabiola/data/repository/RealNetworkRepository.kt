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

import com.mshdabiola.data.Platform
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.network.NetworkDataSource

internal class RealNetworkRepository(
    private val networkSource: NetworkDataSource,
    private val platform: Platform
) : NetworkRepository {
    override suspend fun gotoGoogle(): String {
        return "" // Placeholder, actual implementation would call networkSource
    }

    override suspend fun getLatestReleaseInfo(currentVersion: String): ReleaseInfo {
        if (platform !is Platform.Android)
            return ReleaseInfo.Error("Device not supported")

        val name = "app-${platform.name}-release-unsigned-signed.apk"


        return try {
            val gitHubReleaseInfo = networkSource.getLatestKmtemplateRelease()
            val asset = gitHubReleaseInfo
                .assets
                ?.firstOrNull {
                    it?.browserDownloadUrl?.contains(name) ?: false
                }
            if (asset == null) {
                throw Exception("Asset not found")
            }
            if (versionStringToNumber(currentVersion) >
                versionStringToNumber(gitHubReleaseInfo.tagName ?: "")
            ) {
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

    private fun versionStringToNumber(versionString: String): Long {
        // Remove all non-digit characters (like dots)
        val numericString = versionString.replace(".", "")

        // Convert the resulting string to an integer
        return numericString.toLongOrNull() ?: 1
    }
}
