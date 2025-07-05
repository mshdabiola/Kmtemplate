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
package com.mshdabiola.kotlinmultiplatformtemplate

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KotlinMultiplatformTemplateApp
import com.mshdabiola.kotlinmultiplatformtemplate.ui.shouldUseDarkTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    //    @Inject
//    lateinit var networkMonitor: NetworkMonitor

    private val viewModel: MainAppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val splashScreen = installSplashScreen()
        installSplashScreen()
        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }
        enableEdgeToEdge()
//        splashScreen.setKeepOnScreenCondition {
//            when (uiState) {
//                MainActivityUiState.Loading -> true
//                is MainActivityUiState.Success -> false
//            }
//        }

        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            },
        )
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
//        remoteConfig.fetchAndActivate()
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val updated = task.result
//
//                  val tr=  remoteConfig.getBoolean("theme")
//                  val name  =remoteConfig.getString("name")
//
//                    Timber.e("Config params updated: %s", updated)
//                    Timber.e("theme $tr name $name")
//                    Toast.makeText(this, "Fetch and activate succeeded",
//                        Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "Fetch failed",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
//            override fun onUpdate(configUpdate : ConfigUpdate) {
//                Timber.e("Updated keys: " + configUpdate.updatedKeys);
//
//                if (configUpdate.updatedKeys.contains("name")) {
//                    remoteConfig.activate().addOnCompleteListener {
//                        Timber.e("new name ${remoteConfig.getString("name")}")
//                    }
//                }
//            }
//
//            override fun onError(error : FirebaseRemoteConfigException) {
//                Timber.e( "Config update error with code: " + error.code, error)
//            }
//        })
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    //    Timber.e("Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                //    Timber.e(token)
                // Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            },
        )

        setContent {
            val darkTheme = shouldUseDarkTheme(uiState)

            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle =
                    SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle =
                    SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                )
                onDispose {}
            }

            KotlinMultiplatformTemplateApp()
        }
    }
}

private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)
