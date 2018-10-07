package br.com.adalbertofjr.topgames.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Network
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */

fun isConnected(context: Context): Boolean {
    val connection = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connection.activeNetworkInfo

    return networkInfo != null && networkInfo.isConnectedOrConnecting
}