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
package com.mshdabiola.kotlinmultiplatformtemplate.util

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class TestLifecycleOwner(val composeTestRule: ComposeContentTestRule) : LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        // Initialize the lifecycle state.
        // For many tests, CREATED or STARTED is enough.
        // If you're testing things that react to RESUMED, you might need to advance it further.
        composeTestRule.runOnUiThread { lifecycleRegistry.currentState = Lifecycle.State.STARTED }
    }

    // Helper methods to control lifecycle if needed for specific tests
    fun handleLifecycleEvent(event: Lifecycle.Event) {
        composeTestRule.runOnUiThread { lifecycleRegistry.handleLifecycleEvent(event) }
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
}
