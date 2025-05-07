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
