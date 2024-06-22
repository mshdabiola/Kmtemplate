/*
 *abiola 2022
 */

package com.mshdabiola.hiltmanifest

import androidx.activity.ComponentActivity

/**
 * A [ComponentActivity] annotated with [AndroidEntryPoint] for use in tests, as a workaround
 * for https://github.com/google/dagger/issues/3394
 */
class KoinComponentActivity : ComponentActivity()
