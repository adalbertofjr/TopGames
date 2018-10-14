package br.com.adalbertofjr.topgames.data.repository

import android.content.Context
import br.com.adalbertofjr.topgames.data.api.TwitchAPI
import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.gameslist.domain.model.TopGames

/**
 * Repository
 * Created by Adalberto Fernandes Júnior on 14/10/2018.
 * Copyright © 2018. All rights reserved.
 */
interface Repository {

    fun getMoviesFromNetwork(twitchApi: TwitchAPI, limit: Int, offset: Int?, listener: RepositoryResponseListerner<TopGames>)

    fun getMoviesFromCache(context: Context, listener: RepositoryResponseListerner<TopGames>)

    fun insertMoviesInCache(games: List<Game>, context: Context)

    fun deleteMoviesFromCache(context: Context)
}