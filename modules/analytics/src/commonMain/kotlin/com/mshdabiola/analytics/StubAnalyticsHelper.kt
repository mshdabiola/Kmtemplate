package com.mshdabiola.analytics

/**
 * An implementation of AnalyticsHelper just writes the events to logcat. Used in builds where no
 * analytics events should be sent to a backend.
 */

internal class StubAnalyticsHelper : AnalyticsHelper {
    override fun logEvent(event: AnalyticsEvent) {
//        Log.d(TAG, "Received analytics event: $event")
    }
}
