package br.com.adalbertofjr.topgames.gameslist

import android.net.Uri
import android.util.Log
import br.com.adalbertofjr.topgames.data.api.TwitchAPI
import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.data.api.model.TwitchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ListGamesPresenter
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class ListGamesPresenter(val twitchApi: TwitchAPI) : ListGamesContract.Presenter {
    private val games = mutableListOf<Game>()
    private lateinit var view: ListGamesContract.View

    private val limitOfGames = 100
    private val limitToFetchGames = 13
    private var offset: Int? = null

    private val API_KEY = "0t4py0qo1iqagd5dnig9bheol9yo22"

    override fun loadGames(refresh: Boolean) {
        val gameSize = games.size

        if (gameSize < limitOfGames) {
            view.showLoading(true, refresh)

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
                        games.add(Game(it.game.name, it.game.box, it.game.logo, it.channels, it.viewers))
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
    }

    override fun setView(view: ListGamesContract.View) {
        this.view = view
    }

    override fun onRefresh() {
        offset = null
        games.clear()
        loadGames(true)
    }

    override fun onClickGameDetail(game: Game) {
        view.showGameDetailUI(game)
    }
}