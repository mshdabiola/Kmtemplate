/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

@SuppressLint("MissingPermission")
class ConnectivityManagerNetworkMonitor constructor(
    private val context: Context,
) : NetworkMonitor {
    override val isOnline: Flow<Boolean> =
        callbackFlow {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

            val callback =
                object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        channel.trySend(connectivityManager.isCurrentlyConnected())
                    }

                    override fun onLost(network: Network) {
                        channel.trySend(connectivityManager.isCurrentlyConnected())
                    }

                    override fun onCapabilitiesChanged(
                        network: Network,
                        networkCapabilities: NetworkCapabilities,
                    ) {
                        channel.trySend(connectivityManager.isCurrentlyConnected())
                    }
                }

            connectivityManager?.registerNetworkCallback(
                Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build(),
                callback,
            )

            channel.trySend(connectivityManager.isCurrentlyConnected())

            awaitClose {
                connectivityManager?.unregisterNetworkCallback(callback)
            }
        }
            .conflate()

    @Suppress("DEPRECATION")
    private fun ConnectivityManager?.isCurrentlyConnected() =
        when (this) {
            null -> false

            else -> activeNetworkInfo?.isConnected ?: false
        }
}
