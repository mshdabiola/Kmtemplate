/*
 *abiola 2024
 */

package com.mshdabiola.testing

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * A custom runner to set up the instrumented application class for tests.
 */
class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?,
    ): Application {
        return super.newApplication(classLoader, TestApplication::class.java.name, context)
    }
}
