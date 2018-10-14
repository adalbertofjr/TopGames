package br.com.adalbertofjr.topgames.util

import android.net.ConnectivityManager

/**
 * Network
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */

open class NetworkUtil {
    fun isConnected(connectivityManager: ConnectivityManager): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}