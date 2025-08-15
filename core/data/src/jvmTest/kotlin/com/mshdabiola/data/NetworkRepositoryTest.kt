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
import com.mshdabiola.model.BuildType
import com.mshdabiola.model.Flavor
import com.mshdabiola.model.Platform
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

    private val androidPlatform = Platform.Android(Flavor.FossReliant, BuildType.Release, 30)
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

        val result = repository.getLatestReleaseInfo("0.0.1", allowPreRelease = false)

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
        val result = repository.getLatestReleaseInfo("0.0.1", allowPreRelease = false)

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

        val result = repository.getLatestReleaseInfo("0.0.1", allowPreRelease = false)

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

        val result = repository.getLatestReleaseInfo("2.0.0", allowPreRelease = false)
        // current version

        assertTrue(result is ReleaseInfo.Error)
        assertEquals(
            "Current version is greater than latest version",
            (result as ReleaseInfo.Error).message,
        )
    }

    @Test
    fun `getLatestReleaseInfo returns Error when network call fails`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        networkDataSource.setShouldThrowError(true)

        val result = repository.getLatestReleaseInfo("0.0.1", allowPreRelease = false)

        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Simulated network error", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun getLatestReleaseInfo_currentIsPreRelease_latestIsFullRelease_returnsSuccess() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val currentVersion = "1.0.0-rc1"
        val latestGitHubRelease = GitHubReleaseInfo(
            tagName = "v1.0.0",
            releaseName = "Stable Release 1.0.0",
            body = "This is the full 1.0.0 release.",
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 12345)),
        )
        networkDataSource.setNextReleaseInfo(latestGitHubRelease)

        val result = repository.getLatestReleaseInfo(currentVersion, allowPreRelease = false)

        assertTrue("Result should be Success, but was $result", result is ReleaseInfo.Success)
        val successResult = result as ReleaseInfo.Success
        assertEquals("v1.0.0", successResult.tagName)
        assertEquals("Stable Release 1.0.0", successResult.releaseName)
        assertEquals("This is the full 1.0.0 release.", successResult.body)
        assertEquals("app-fossReliant-release-unsigned-signed.apk", successResult.asset)
    }

    // --- New Comprehensive Tests ---

    @Test
    fun `getLatestReleaseInfo success with different flavor and buildType`() = runTest {
        val googlePlayDebugPlatform = Platform.Android(Flavor.GooglePlay, BuildType.Debug, 30)
        repository = RealNetworkRepository(networkDataSource, googlePlayDebugPlatform)
        val expectedAssetName = "app-googlePlay-debug-unsigned-signed.apk"
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0",
            releaseName = "GP Debug Release",
            body = "Body for GP Debug",
            assets = listOf(Asset(browserDownloadUrl = expectedAssetName, size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("0.1.0", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Success)
        assertEquals(expectedAssetName, (result as ReleaseInfo.Success).asset)
        assertEquals("v1.0.0", result.tagName)
    }

    @Test
    fun `getLatestReleaseInfo error when currentVersion is invalid`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0",
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("1.bad.0", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Invalid version format", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo error when online tagName is invalid`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v-bad.1.0", // Invalid tag
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Invalid version format", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo error when online tagName is null`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = null,
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Invalid version format", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo error when online tagName is empty`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "",
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Invalid version format", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun getLatestReleaseInfo_error_when_versions_are_equal_full() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0",
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Error)
        assertEquals(
            "Current version is equal to latest version",
            (result as ReleaseInfo.Error).message,
        )
    }

    @Test
    fun `getLatestReleaseInfo success when online is newer pre-release`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.1-alpha1",
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Success)
        assertEquals("v1.0.1-alpha1", (result as ReleaseInfo.Success).tagName)
    }

    @Test
    fun `getLatestReleaseInfo success when online is newer pre-release (same base)`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0-alpha2",
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("1.0.0-alpha1", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Success)
        assertEquals("v1.0.0-alpha2", (result as ReleaseInfo.Success).tagName)
    }

    @Test
    fun `getLatestReleaseInfo error when online is older pre-release type`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0-alpha2",
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("1.0.0-beta1", allowPreRelease = false)
        // current is beta, online is alpha
        assertTrue(result is ReleaseInfo.Error)
        assertEquals(
            "Current version is greater than latest version",
            (result as ReleaseInfo.Error).message,
        )
    }

    @Test
    fun getLatestReleaseInfo_error_when_versions_are_equal_pre_release() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0-rc1",
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("1.0.0-rc1", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Error)
        assertEquals(
            "Current version is equal to latest version",
            (result as ReleaseInfo.Error).message,
        )
    }

    @Test
    fun `getLatestReleaseInfo error when assets list is null`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0",
            assets = null, // Null asset list
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("0.1.0", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Asset not found", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo error when assets list is empty`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0",
            assets = emptyList(), // Empty asset list
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("0.1.0", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Asset not found", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo error when asset browserDownloadUrl is null`() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val releaseInfo = GitHubReleaseInfo(
            tagName = "v1.0.0",
            assets = listOf(Asset(browserDownloadUrl = null, size = 100)), // Asset with null URL
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("0.1.0", allowPreRelease = false)
        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Asset not found", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo success when current version has no pre-release and online has pre-release but older`() =
        runTest {
            repository = RealNetworkRepository(networkDataSource, androidPlatform)
            networkDataSource.setNextReleaseInfo(
                GitHubReleaseInfo(
                    tagName = "v0.9.0-rc1", // Older pre-release
                    releaseName = "Old RC",
                    body = "Body",
                    assets = listOf(Asset("app-fossReliant-release-unsigned-signed.apk", 1)),
                ),
            )
            val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
            // Current is full release
            assertTrue("Expected error as current is newer, got $result", result is ReleaseInfo.Error)
            assertEquals(
                "Current version is greater than latest version",
                (result as ReleaseInfo.Error).message,
            )
        }

    @Test
    fun `getLatestReleaseInfo success when current version has pre-release and online has older full release`() =
        runTest {
            repository = RealNetworkRepository(networkDataSource, androidPlatform)
            networkDataSource.setNextReleaseInfo(
                GitHubReleaseInfo(
                    tagName = "v0.9.0", // Older full release
                    releaseName = "Old Full",
                    body = "Body",
                    assets = listOf(Asset("app-fossReliant-release-unsigned-signed.apk", 1)),
                ),
            )
            val result = repository.getLatestReleaseInfo("1.0.0-alpha1", allowPreRelease = false)
            // Current is newer pre-release
            assertTrue(
                "Expected error as current is newer, got $result",
                result is ReleaseInfo.Error,
            )
            assertEquals(
                "Current version is greater than latest version",
                (result as ReleaseInfo.Error).message,
            )
        }

    @Test
    fun `gotoGoogle returns empty string as per placeholder implementation`() = runTest {
        // Platform doesn't matter for this test as per current implementation
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val result = repository.gotoGoogle()
        assertEquals("", result)
    }

    @Test
    fun getLatestReleaseInfo_allowPreReleaseTrue_onlineIsPreRelease_throwsError() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val preReleaseOnline = GitHubReleaseInfo(
            tagName = "v1.0.0-rc1",
            releaseName = "Pre-release Candidate",
            body = "This is a release candidate.",
            assets = listOf(Asset(browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk", size = 100)),
        )
        networkDataSource.setNextReleaseInfo(preReleaseOnline)

        val result = repository.getLatestReleaseInfo("0.9.0", allowPreRelease = true)

        assertTrue(result is ReleaseInfo.Error)
        assertEquals("Pre-release versions are not allowed", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun getLatestReleaseInfo_allowPreReleaseFalse_onlineIsPreRelease_returnsSuccess() = runTest {
        repository = RealNetworkRepository(networkDataSource, androidPlatform)
        val onlineVersionTag = "v1.1.0-alpha1"
        val expectedAssetName = "app-fossReliant-release-unsigned-signed.apk"
        val preReleaseOnline = GitHubReleaseInfo(
            tagName = onlineVersionTag,
            releaseName = "Alpha release",
            body = "This is an alpha test release.",
            assets = listOf(Asset(browserDownloadUrl = expectedAssetName, size = 120)),
        )
        networkDataSource.setNextReleaseInfo(preReleaseOnline)

        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)

        assertTrue("Result should be Success, but was $result", result is ReleaseInfo.Success)
        val successResult = result as ReleaseInfo.Success
        assertEquals(onlineVersionTag, successResult.tagName)
        assertEquals("Alpha release", successResult.releaseName)
        assertEquals("This is an alpha test release.", successResult.body)
        assertEquals(expectedAssetName, successResult.asset)
    }
}
