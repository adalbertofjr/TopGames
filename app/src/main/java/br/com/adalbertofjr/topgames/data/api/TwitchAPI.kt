package br.com.adalbertofjr.topgames.data.api

import br.com.adalbertofjr.topgames.data.api.model.TwitchData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * TwitchAPI
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */


interface TwitchAPI {

    @GET("games/top")
    abstract fun getTopGames(@Header("Client-ID") clientID: String): Call<TwitchData>
}