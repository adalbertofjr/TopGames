package br.com.adalbertofjr.topgames.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.util.Log
import br.com.adalbertofjr.topgames.data.api.TwitchAPI
import br.com.adalbertofjr.topgames.data.api.model.*
import br.com.adalbertofjr.topgames.data.local.TopGamesContract
import br.com.adalbertofjr.topgames.gameslist.domain.model.TopGames
import br.com.adalbertofjr.topgames.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * TopMoviesRepository
 * Created by Adalberto Fernandes Júnior on 14/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class TopMoviesRepository : Repository {


    override fun getMoviesFromNetwork(twitchApi: TwitchAPI, limit: Int, offset: Int?, listener: RepositoryResponseListerner<TopGames>) {
        val twitchCall: Call<TwitchData>
        val games = mutableListOf<Game>()

        twitchCall = twitchApi.getTopGames(Constants.API_KEY, limit, offset)
        twitchCall.enqueue(object : Callback<TwitchData> {
            override fun onResponse(call: Call<TwitchData>, response: Response<TwitchData>) {
                val top = response.body()!!.top
                val links = response.body()!!.links

                top.forEach {
                    Log.i("Jogo: ", it.game.toString())
                    val game = Game(it.game.name, it.game.box, it.game.logo, it.channels, it.viewers)
                    games.add(game)
                }

                var topGames = TopGames(games, links)

                listener.onSuccess(topGames)
            }

            override fun onFailure(call: Call<TwitchData>, t: Throwable) {
                t.printStackTrace()

                listener.onFailure(t.message!!)
            }
        })
    }

    override fun getMoviesFromCache(context: Context, listener: RepositoryResponseListerner<TopGames>) {

        val games = mutableListOf<Game>()

        val cursor = context.getContentResolver().query(
                TopGamesContract.TopGamesEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        )

        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex(TopGamesContract.TopGamesEntry.COLUMN_NAME))
            val box = cursor.getString(cursor.getColumnIndex(TopGamesContract.TopGamesEntry.COLUMN_BOX))
            val logo = cursor.getString(cursor.getColumnIndex(TopGamesContract.TopGamesEntry.COLUMN_LOGO))
            val channels = cursor.getInt(cursor.getColumnIndex(TopGamesContract.TopGamesEntry.COLUMN_CHANNELS))
            val viewers = cursor.getInt(cursor.getColumnIndex(TopGamesContract.TopGamesEntry.COLUMN_VIEWERS))
            val game = Game(name, Box(box), Logo(logo), channels, viewers)
            games.add(game)
        }

        var topGames = TopGames(games, Links("", ""))

        if (games.size > 0) {
            listener.onSuccess(topGames)
        } else {
            listener.onFailure("Não há jogos armazenados no cache")
        }
    }

    override fun insertMoviesInCache(games: List<Game>, context: Context) {
        games.forEach {
            val gameValue = ContentValues()
            gameValue.put(TopGamesContract.TopGamesEntry.COLUMN_NAME, it.name)
            gameValue.put(TopGamesContract.TopGamesEntry.COLUMN_BOX, it.box.large)
            gameValue.put(TopGamesContract.TopGamesEntry.COLUMN_LOGO, it.box.large)
            gameValue.put(TopGamesContract.TopGamesEntry.COLUMN_CHANNELS, it.channels)
            gameValue.put(TopGamesContract.TopGamesEntry.COLUMN_VIEWERS, it.viewers)

            try {
                context.contentResolver.insert(TopGamesContract.TopGamesEntry.CONTENT_URI, gameValue)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }

    override fun deleteMoviesFromCache(context: Context) {
        context.contentResolver.delete(TopGamesContract.TopGamesEntry.CONTENT_URI, null, null)
    }
}

