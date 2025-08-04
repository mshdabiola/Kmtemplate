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
package com.mshdabiola.data

import com.mshdabiola.data.doubles.TestNetworkDataSource
import com.mshdabiola.data.repository.RealNetworkRepository
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.network.model.Asset
import com.mshdabiola.network.model.GitHubReleaseInfo
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NetworkRepositoryTest {

    private lateinit var networkDataSource: TestNetworkDataSource
    private lateinit var repository: RealNetworkRepository

    private val androidPlatform = Platform.Android.FossReliant("fossReliant", 30)
    private val nonAndroidPlatform = Platform.Desktop("desktop", "11")

    @Before
    fun setUp() {
        networkDataSource = TestNetworkDataSource()
    }

    @Test
    fun `getLatestReleaseInfo returns Success when platform is Android and asset is found`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val expectedReleaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0",
            releaseName = "Test Release",
            body = "Release body",
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(expectedReleaseInfo)

        val result = repository.getLatestReleaseInfo("0.0.1")

        assertTrue(result is ReleaseInfo.Success)
        val successResult = result as ReleaseInfo.Success
        assertEquals("v1.0.0", successResult.tagName)
        assertEquals("Test Release", successResult.releaseName)
        assertEquals("Release body", successResult.body)
        assertEquals("app-fossReliant-release-unsigned-signed.apk", successResult.asset)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when platform is not Android`() = runTest {
        repository = RealNetworkRepository(networkDataSource, nonAndroidPlatform)
        val result = repository.getLatestReleaseInfo("0.0.1")

        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Device not supported", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when asset is not found`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfoWithNoMatchingAsset = GitHubReleaseInfo(
            tagName = "v1.0.0",
            assets = listOf(Asset(browserDownloadUrl = "wrong-asset.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfoWithNoMatchingAsset)

        val result = repository.getLatestReleaseInfo("0.0.1")

        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Asset not found", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when current version is greater than latest version`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
         val expectedReleaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0", // latest version
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(expectedReleaseInfo)

        val result = repository.getLatestReleaseInfo("2.0.0") // current version

        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Current version is greater than latest version", (result as ReleaseInfo.Error).message)
    }


    @Test
    fun `getLatestReleaseInfo returns Error when network call fails`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        networkDataSource.setShouldThrowError(true)

        val result = repository.getLatestReleaseInfo("0.0.1")

        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Simulated network error", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `gotoGoogle returns empty string as per placeholder implementation`() = runTest {
        // Platform doesn't matter for this test as per current implementation
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val result = repository.gotoGoogle()
        assertEquals("", result)
    }
}
