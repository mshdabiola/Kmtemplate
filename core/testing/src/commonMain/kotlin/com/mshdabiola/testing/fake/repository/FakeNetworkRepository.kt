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
package com.mshdabiola.testing.fake.repository

import com.mshdabiola.data.repository.NetworkRepository
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.model.UpdateException

class FakeNetworkRepository : NetworkRepository {
    private var nextReleaseInfo: ReleaseInfo = ReleaseInfo.NewUpdate(
        asset = "",
        body = "body",
        releaseName = "releaseName",
        tagName = "tagName",
    )
    private var shouldThrowError: Boolean = false
    private var errorMessage: String = "Default error message"

    /**
     * Fake implementation that simulates navigating to Google.
     *
     * Returns a fixed success string used by tests.
     *
     * @return A constant string "got to google" indicating the simulated navigation result.
     */
    override suspend fun gotoGoogle(): String {
        return "got to google"
    }

    /**
     * Returns the configured release information for the given current version.
     *
     * This is a test/fake implementation that consults internal state:
     * - If `shouldThrowError` is true, returns `ReleaseInfo.Error` with the configured `errorMessage`.
     * - Otherwise returns the `nextReleaseInfo` previously set via `setNextReleaseInfo`.
     *
     * @param currentVersion The current app version (ignored by this fake; accepted for API compatibility).
     * @param allowPreRelease Whether pre-release versions are allowed (currently ignored by this fake).
     * @return A [ReleaseInfo] representing either the configured success value or an error.*/
    override suspend fun getLatestReleaseInfo(
        currentVersion: String,
        allowPreRelease: Boolean,
    ): ReleaseInfo {
        return if (shouldThrowError) {
            ReleaseInfo.Error(UpdateException(errorMessage))
        } else {
            nextReleaseInfo
        }
    }

    fun setNextReleaseInfo(expectedReleaseInfo: ReleaseInfo) {
        this.nextReleaseInfo = expectedReleaseInfo
        this.shouldThrowError = false
    }

    fun setShouldThrowError(shouldThrow: Boolean, message: String) {
        this.shouldThrowError = shouldThrow
        this.errorMessage = message
        // Optionally, clear the successful release info if an error is to be thrown
        // this.nextReleaseInfo = ReleaseInfo.Error("Not set, will throw error")
    }
}
