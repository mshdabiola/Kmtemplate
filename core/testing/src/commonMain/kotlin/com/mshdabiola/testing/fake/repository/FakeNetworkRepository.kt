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

class FakeNetworkRepository : NetworkRepository {
    private var nextReleaseInfo: ReleaseInfo = ReleaseInfo.Success(
        asset = "",
        body = "body",
        releaseName = "releaseName",
        tagName = "tagName",
    )
    private var shouldThrowError: Boolean = false
    private var errorMessage: String = "Default error message"

    override suspend fun gotoGoogle(): String {
        return "got to google"
    }

    override suspend fun getLatestReleaseInfo(currentVersion: String): ReleaseInfo {
        return if (shouldThrowError) {
            ReleaseInfo.Error(errorMessage)
        } else {
            nextReleaseInfo
        }
    }

    fun setNextReleaseInfo(expectedReleaseInfo: ReleaseInfo.Success) {
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
