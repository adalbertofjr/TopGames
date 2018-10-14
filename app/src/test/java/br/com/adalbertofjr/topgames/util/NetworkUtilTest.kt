package br.com.adalbertofjr.topgames.util

import android.net.ConnectivityManager
import android.net.NetworkInfo
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner

/**
 * NetworkTest
 * Created by Adalberto Fernandes Júnior on 14/10/2018.
 * Copyright © 2018. All rights reserved.
 */
@RunWith(MockitoJUnitRunner::class)
class NetworkUtilTest {

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    @Mock
    private lateinit var networkInfo: NetworkInfo

    @Test
    fun should_ReturnTrue_WhenIsConnected() {
        Mockito.`when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        Mockito.`when`(networkInfo.isConnectedOrConnecting).thenReturn(true)

        NetworkUtil().isConnected(connectivityManager)

        Mockito.verify(connectivityManager, times(1)).activeNetworkInfo
        Mockito.verify(networkInfo, times(1)).isConnectedOrConnecting
        Assert.assertTrue(networkInfo.isConnectedOrConnecting)
    }

    @Test
    fun should_ReturnFalse_WhenIsNotConnected() {
        Mockito.`when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        Mockito.`when`(networkInfo.isConnectedOrConnecting).thenReturn(false)

        NetworkUtil().isConnected(connectivityManager)

        Mockito.verify(connectivityManager, times(1)).activeNetworkInfo
        Mockito.verify(networkInfo, times(1)).isConnectedOrConnecting
        Assert.assertFalse(networkInfo.isConnectedOrConnecting)
    }
}