/*
 *abiola 2022
 */

package com.mshdabiola.testing.fake

import com.mshdabiola.data.util.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AlwaysOnlineNetworkMonitor : NetworkMonitor {
    override val isOnline: Flow<Boolean> = flowOf(true)
}
