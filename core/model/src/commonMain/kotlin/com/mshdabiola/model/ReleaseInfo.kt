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
package com.mshdabiola.model

sealed class ReleaseInfo {
    /**
     * Represents an error that occurred while fetching or processing release information.
     * @param message A descriptive message of the error.
     */
    data class Error(val message: String) : ReleaseInfo()

    /**
     * Represents a successful check where a new update is available.
     * @param tagName The tag name of the new release (e.g., "v1.2.0").
     * @param releaseName The human-readable name of the release (e.g., "Version 1.2.0").
     * @param body A description or changelog for the new release.
     * @param asset The URL to the downloadable asset for the update.
     */
    data class NewUpdate(
        val tagName: String,
        val releaseName: String,
        val body: String,
        val asset: String,
    ) : ReleaseInfo()

    /**
     * Represents a state where the current version is up to date and no new update is available.
     */
    object UpToDate : ReleaseInfo()
}
