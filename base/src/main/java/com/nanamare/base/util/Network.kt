package com.nanamare.base.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface NetworkConnection {
    fun isConnected(): Boolean
    fun registerNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback)
    fun unRegisterNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback)
}

open class NetworkConnectionImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkConnection {

    private val connectivityManager by lazy { context.getSystemService<ConnectivityManager>() }

    private val networkRequestBuilder: NetworkRequest.Builder by lazy {
        NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
    }

    override fun isConnected(): Boolean =
        connectivityManager?.activeNetworkInfo?.isConnected == true

    override fun registerNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        connectivityManager?.registerNetworkCallback(networkRequestBuilder.build(), networkCallback)
    }

    override fun unRegisterNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }

}
