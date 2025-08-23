/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.data.doubles

import com.mshdabiola.network.NetworkDataSource
import com.mshdabiola.network.model.Asset
import com.mshdabiola.network.model.GitHubReleaseInfo

class TestNetworkDataSource : NetworkDataSource {
    private var nextReleaseInfo: GitHubReleaseInfo? = null
    private var shouldThrowError: Boolean = false

    fun setNextReleaseInfo(releaseInfo: GitHubReleaseInfo) {
        nextReleaseInfo = releaseInfo
        shouldThrowError = false
    }

    fun setShouldThrowError(throwError: Boolean) {
        shouldThrowError = throwError
        nextReleaseInfo = null
    }

    override suspend fun goToGoogle(): String {
        return "Fake Google Response"
    }

    override suspend fun getLatestKmtemplateRelease(): GitHubReleaseInfo {
        if (shouldThrowError) {
            throw Exception("Simulated network error")
        }
        return nextReleaseInfo ?: GitHubReleaseInfo(
            htmlUrl = "https://github.com/mshdabiola/Kmtemplate/releases/tag/v0.0.1",
            tagName = "v0.0.1",
            releaseName = "Initial Release",
            assets = listOf(
                Asset(
                    size = 123456,
                    browserDownloadUrl = "https://github.com/mshdabiola/Kmtemplate/" +
                        "releases/download/v0.0.1/app-debug-release-unsigned-signed.apk",
                ),
                Asset(
                    size = 123456,
                    browserDownloadUrl = "https://github.com/mshdabiola/Kmtemplate/" +
                        "releases/download/v0.0.1/app-release-release-unsigned-signed.apk",
                ),
            ),
            body = "This is the initial release of Kmtemplate.",
            prerelease = false,
        )
    }
}
