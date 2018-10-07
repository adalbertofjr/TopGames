package br.com.adalbertofjr.topgames.data.api

import br.com.adalbertofjr.topgames.data.api.model.TwitchData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * TwitchAPI
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */


interface TwitchAPI {

    @GET("games/top")
    fun getTopGames(
            @Header("Client-ID") clientID: String,
            @Query("limit") limit: Int,
            @Query("offset") offset: Int?): Call<TwitchData>
}