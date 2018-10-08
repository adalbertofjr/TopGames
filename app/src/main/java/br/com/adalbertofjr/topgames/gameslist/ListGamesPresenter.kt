package br.com.adalbertofjr.topgames.gameslist

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import br.com.adalbertofjr.topgames.data.api.TwitchAPI
import br.com.adalbertofjr.topgames.data.api.model.Box
import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.data.api.model.Logo
import br.com.adalbertofjr.topgames.data.api.model.TwitchData
import br.com.adalbertofjr.topgames.data.local.TopGamesContract
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.COLUMN_BOX
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.COLUMN_CHANNELS
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.COLUMN_LOGO
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.COLUMN_NAME
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.COLUMN_VIEWERS
import br.com.adalbertofjr.topgames.util.isConnected
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ListGamesPresenter
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class ListGamesPresenter(val twitchApi: TwitchAPI, val activity: ListGamesActivity) : ListGamesContract.Presenter {

    private val games = mutableListOf<Game>()
    private lateinit var view: ListGamesContract.View

    private val limitOfGames = 100
    private val limitToFetchGames = 13
    private var offset: Int? = null
    private var inCache: Boolean = false

    private val API_KEY = "0t4py0qo1iqagd5dnig9bheol9yo22"

    override fun loadGames(refresh: Boolean) {
        // Connection
        if (!checkConnection(this.activity)) {
            view.showSnackBar("Problema com conexão de internet")
            fetchGamesInCache(refresh)
        } else {
            view.showErrorConnection(false)
            val gameSize = games.size
            if (gameSize < limitOfGames) {
                view.showLoading(true, refresh)
                fetchGames(gameSize, refresh)
            }
        }
    }

    override fun setView(view: ListGamesContract.View) {
        this.view = view
    }

    override fun checkConnection(context: Context): Boolean {
        return isConnected(context)
    }

    override fun onRefresh() {
        offset = null
        games.clear()

        if (checkConnection(activity)) {
            activity.contentResolver.delete(TopGamesContract.TopGamesEntry.CONTENT_URI, null, null)
        }

        loadGames(true)
    }

    override fun onClickGameDetail(game: Game) {
        view.showGameDetailUI(game)
    }

    /**
     * Buscando os jogos
     */
    private fun fetchGames(gameSize: Int, refresh: Boolean) {
        if (inCache) {
            games.clear()
            inCache = false
        }

        val twitchCall: Call<TwitchData>
        val limit = if (limitToFetchGames + gameSize > limitOfGames) limitOfGames - gameSize else limitToFetchGames

        twitchCall = twitchApi.getTopGames(API_KEY, limit, offset)
        twitchCall.enqueue(object : Callback<TwitchData> {
            override fun onResponse(call: Call<TwitchData>, response: Response<TwitchData>) {
                val links = response.body()!!.links
                val topGames = response.body()!!.top
                offset = Uri.parse(links.next).getQueryParameter("offset").toInt()

                topGames.forEach {
                    Log.i("Jogo: ", it.game.toString())
                    val game = Game(it.game.name, it.game.box, it.game.logo, it.channels, it.viewers)
                    games.add(game)
                    insertLocalDatabase(game)
                }

                view.showLoading(false, refresh)
                view.showGames(games)
            }

            override fun onFailure(call: Call<TwitchData>, t: Throwable) {
                t.printStackTrace()
                view.showLoading(false, refresh)
            }
        })
    }


    /**
     * Buscando os jogos no cache
     */
    private fun fetchGamesInCache(refresh: Boolean) {
        if (games.size <= 0) {
            inCache = true
            val cursor = activity.getContentResolver().query(
                    TopGamesContract.TopGamesEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            )

            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val box = cursor.getString(cursor.getColumnIndex(COLUMN_BOX))
                val logo = cursor.getString(cursor.getColumnIndex(COLUMN_LOGO))
                val channels = cursor.getInt(cursor.getColumnIndex(COLUMN_CHANNELS))
                val viewers = cursor.getInt(cursor.getColumnIndex(COLUMN_VIEWERS))
                val game = Game(name, Box(box), Logo(logo), channels, viewers)
                games.add(game)
            }

            if (games.size > 0) {
                view.showGames(games)
            }else{
                view.showErrorConnection(true)
            }
        }
        view.showLoading(false, refresh)
    }

    /**
     * Inserindo jogo no bd.
     */
    private fun insertLocalDatabase(game: Game) {
        try {
            val gameValue = ContentValues()
            gameValue.put(COLUMN_NAME, game.name)
            gameValue.put(COLUMN_BOX, game.box.large)
            gameValue.put(COLUMN_LOGO, game.box.large)
            gameValue.put(COLUMN_CHANNELS, game.channels)
            gameValue.put(COLUMN_VIEWERS, game.viewers)

            activity.contentResolver.insert(TopGamesContract.TopGamesEntry.CONTENT_URI, gameValue)
            Log.i("Inserted", game.name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}