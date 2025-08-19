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

    private val fossReleasePlatform = Platform.Android(Flavor.FossReliant, BuildType.Release, 30)
    private val googlePlayDebugPlatform = Platform.Android(Flavor.GooglePlay, BuildType.Debug, 31)
    private val nonAndroidPlatform = Platform.Desktop("linux", "kernel6")

    @Before
    fun setUp() {
        networkDataSource = TestNetworkDataSource()
    }

    private fun createGitHubReleaseInfo(
        tagName: String? = "v1.0.0",
        releaseName: String? = "Test Release",
        body: String? = "Release body",
        assets: List<Asset?>? = listOf(
            Asset(
                browserDownloadUrl = "app-fossReliant-release-unsigned-signed.apk",
                size = 100,
            ),
        ),
        prerelease: Boolean? = false,
    ): GitHubReleaseInfo {
        return GitHubReleaseInfo(
            tagName = tagName,
            releaseName = releaseName,
            body = body,
            assets = assets,
            prerelease = prerelease,
        )
    }

    @Test
    fun `getLatestReleaseInfo returns NewUpdate when online version is newer (full release)`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        val releaseInfo = createGitHubReleaseInfo(
            tagName = "v1.0.0",
            assets = listOf(Asset("app-fossReliant-release-unsigned-signed.apk", 100)),
            prerelease = false,
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("0.9.0", allowPreRelease = false)

        assertTrue("Expected NewUpdate, got $result", result is ReleaseInfo.NewUpdate)
        val newUpdate = result as ReleaseInfo.NewUpdate
        assertEquals("v1.0.0", newUpdate.tagName)
        assertEquals("Test Release", newUpdate.releaseName)
        assertEquals("Release body", newUpdate.body)
        assertEquals("app-fossReliant-release-unsigned-signed.apk", newUpdate.asset)
    }

    @Test
    fun `getLatestReleaseInfo returns NewUpdate for different flavor and buildType`() = runTest {
        repository = RealNetworkRepository(networkDataSource, googlePlayDebugPlatform)
        val expectedAssetName = "app-googlePlay-debug-unsigned-signed.apk"
        val releaseInfo = createGitHubReleaseInfo(
            tagName = "v2.0.0",
            assets = listOf(Asset(expectedAssetName, 200)),
            prerelease = false,
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)

        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)

        assertTrue("Expected NewUpdate, got $result", result is ReleaseInfo.NewUpdate)
        val newUpdate = result as ReleaseInfo.NewUpdate
        assertEquals(expectedAssetName, newUpdate.asset)
        assertEquals("v2.0.0", newUpdate.tagName)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when platform is not Android`() = runTest {
        repository = RealNetworkRepository(networkDataSource, nonAndroidPlatform)
        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Device not supported", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when asset is not found (wrong name)`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        val releaseInfo = createGitHubReleaseInfo(
            assets = listOf(Asset("wrong-asset.apk", 100)),
        )
        networkDataSource.setNextReleaseInfo(releaseInfo)
        val result = repository.getLatestReleaseInfo("0.1.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Asset not found", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when assets list is null`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        val releaseInfo = createGitHubReleaseInfo(assets = null)
        networkDataSource.setNextReleaseInfo(releaseInfo)
        val result = repository.getLatestReleaseInfo("0.1.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Asset not found", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when assets list is empty`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        val releaseInfo = createGitHubReleaseInfo(assets = emptyList())
        networkDataSource.setNextReleaseInfo(releaseInfo)
        val result = repository.getLatestReleaseInfo("0.1.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Asset not found", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when asset browserDownloadUrl is null`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        val releaseInfo = createGitHubReleaseInfo(assets = listOf(Asset(browserDownloadUrl = null, size = 100)))
        networkDataSource.setNextReleaseInfo(releaseInfo)
        val result = repository.getLatestReleaseInfo("0.1.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Asset not found", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when currentVersion is invalid`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo())
        val result = repository.getLatestReleaseInfo("1.bad.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Invalid version format", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when online tagName is invalid`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = "v-bad.1.0"))
        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Invalid version format", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when online tagName is null`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = null))
        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Invalid version format", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when online tagName is empty`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = ""))
        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Invalid version format", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo returns UpToDate when versions are equal (full release)`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = "v1.0.0"))
        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        // This assertion will FAIL until RealNetworkRepository is updated
        // to return ReleaseInfo.UpToDate for equal versions.
        // Currently, it returns Error("Current version is equal to latest version")
        assertEquals(ReleaseInfo.UpToDate, result)
    }

    @Test
    fun `getLatestReleaseInfo returns Error when current version is greater (full release)`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = "v1.0.0"))
        val result = repository.getLatestReleaseInfo("2.0.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals(
            "Current version is greater than latest version",
            (result as ReleaseInfo.Error).message,
        )
    }

    @Test
    fun `getLatestReleaseInfo NewUpdate when online is newer pre-release and allowPreRelease is true`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        val releaseInfo = createGitHubReleaseInfo(tagName = "v1.0.1-alpha1", prerelease = true)
        networkDataSource.setNextReleaseInfo(releaseInfo)
        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = true)
        assertTrue("Expected NewUpdate, got $result", result is ReleaseInfo.NewUpdate)
        assertEquals("v1.0.1-alpha1", (result as ReleaseInfo.NewUpdate).tagName)
    }

    @Test
    fun `getLatestReleaseInfo NewUpdate when online is newer pre-release (same base) and allowPreRelease is true`() =
        runTest {
            repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
            val releaseInfo = createGitHubReleaseInfo(tagName = "v1.0.0-beta1", prerelease = true)
            networkDataSource.setNextReleaseInfo(releaseInfo)
            val result = repository.getLatestReleaseInfo("1.0.0-alpha2", allowPreRelease = true)
            assertTrue("Expected NewUpdate, got $result", result is ReleaseInfo.NewUpdate)
            assertEquals("v1.0.0-beta1", (result as ReleaseInfo.NewUpdate).tagName)
        }

    @Test
    fun `getLatestReleaseInfo error when online is pre-release and allowPreRelease is false`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = "v1.0.1-alpha1", prerelease = true))
        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Pre-release versions are not allowed", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `getLatestReleaseInfo NewUpdate when online prerelease is null and allowPreRelease is false`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        val releaseInfo = createGitHubReleaseInfo(tagName = "v1.1.0", prerelease = null) // prerelease is null
        networkDataSource.setNextReleaseInfo(releaseInfo)
        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = false)
        assertTrue("Expected NewUpdate, got $result", result is ReleaseInfo.NewUpdate)
        assertEquals("v1.1.0", (result as ReleaseInfo.NewUpdate).tagName)
    }

    @Test
    fun `getLatestReleaseInfo error when online is older pre-release type and allowPreRelease is true`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = "v1.0.0-alpha2", prerelease = true))
        val result = repository.getLatestReleaseInfo("1.0.0-beta1", allowPreRelease = true)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals(
            "Current version is greater than latest version",
            (result as ReleaseInfo.Error).message,
        )
    }

    @Test
    fun `getLatestReleaseInfo UpToDate when versions are equal (pre-release) and allowPreRelease is true`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = "v1.0.0-rc1", prerelease = true))
        val result = repository.getLatestReleaseInfo("1.0.0-rc1", allowPreRelease = true)
        // This assertion will FAIL until RealNetworkRepository is updated
        // to return ReleaseInfo.UpToDate for equal versions.
        // Currently, it returns Error("Current version is equal to latest version")
        assertEquals(ReleaseInfo.UpToDate, result)
    }

    @Test
    fun `gtNewUpdate when current is pre-release and online is newer full release (allowPreRelease false)`() =
        runTest {
            repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
            networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = "v1.0.0", prerelease = false))
            val result = repository.getLatestReleaseInfo("0.9.0-rc1", allowPreRelease = false)
            assertTrue("Expected NewUpdate, got $result", result is ReleaseInfo.NewUpdate)
            assertEquals("v1.0.0", (result as ReleaseInfo.NewUpdate).tagName)
        }

    @Test
    fun `gi NewUpdate when current is pre-release and online is newer full release (allowPreRelease true)`() =
        runTest {
            repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
            networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = "v1.0.0", prerelease = false))
            val result = repository.getLatestReleaseInfo("0.9.0-rc1", allowPreRelease = true)
            assertTrue("Expected NewUpdate, got $result", result is ReleaseInfo.NewUpdate)
            assertEquals("v1.0.0", (result as ReleaseInfo.NewUpdate).tagName)
        }

    @Test
    fun `gi error when current is full release and online is older pre-release (allowPreRelease true)`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setNextReleaseInfo(createGitHubReleaseInfo(tagName = "v0.9.0-rc1", prerelease = true))
        val result = repository.getLatestReleaseInfo("1.0.0", allowPreRelease = true)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals(
            "Current version is greater than latest version",
            (result as ReleaseInfo.Error).message,
        )
    }

    @Test
    fun `getLatestReleaseInfo error when network call itself fails`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        networkDataSource.setShouldThrowError(true)
        val result = repository.getLatestReleaseInfo("0.1.0", allowPreRelease = false)
        assertTrue("Expected Error, got $result", result is ReleaseInfo.Error)
        assertEquals("Simulated network error", (result as ReleaseInfo.Error).message)
    }

    @Test
    fun `gotoGoogle returns empty string as per current placeholder implementation`() = runTest {
        repository = RealNetworkRepository(networkDataSource, fossReleasePlatform)
        val result = repository.gotoGoogle()
        assertEquals("", result)
    }
}
