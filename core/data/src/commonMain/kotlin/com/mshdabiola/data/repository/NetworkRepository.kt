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

import com.mshdabiola.model.ReleaseInfo

interface NetworkRepository {

    /**
     * Performs a network request to Google and returns the response body.
     *
     * This suspending function issues a network call to Google's endpoint and returns the
     * response payload as a raw string.
     *
     * @return The response body from Google as a String.
     */
    suspend fun gotoGoogle(): String

    /**
     * Retrieve information about the latest available release.
     *
     * Suspends while querying the release source and returns metadata for the most-recent release.
     * The `currentVersion` is used for comparison to determine whether the returned release is newer.
     * If `allowPreRelease` is true, pre-release (e.g., beta or release-candidate) versions are considered when choosing the latest release.
     *
     * @param currentVersion the currently installed/running semantic version string used for comparison.
     * @param allowPreRelease whether to include pre-release versions when selecting the latest release (default false).
     * @return a [ReleaseInfo] describing the latest release found.
     */
    suspend fun getLatestReleaseInfo(currentVersion: String, allowPreRelease: Boolean = false): ReleaseInfo
}
