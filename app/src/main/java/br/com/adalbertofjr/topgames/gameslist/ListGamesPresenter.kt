package br.com.adalbertofjr.topgames.gameslist

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


    private lateinit var view: ListGamesContract.View

    override fun loadGames() {
        view.showLoading(true)

        val twitchCall = twitchApi.getTopGames("0t4py0qo1iqagd5dnig9bheol9yo22")
        twitchCall.enqueue(object : Callback<TwitchData> {
            override fun onResponse(call: Call<TwitchData>, response: Response<TwitchData>) {
                val topGames = response.body()!!.top


                val games = mutableListOf<Game>()

                for (game in topGames) {
                    Log.i("Jogo: ", game.toString())
                    games.add(Game(game.game.name, game.game.box, game.game.logo))
                }

                view.showGames(games)
                view.showLoading(false)
            }

            override fun onFailure(call: Call<TwitchData>, t: Throwable) {
                t.printStackTrace()
                view.showLoading(false)
            }
        })
    }

    override fun setView(view: ListGamesContract.View) {
        this.view = view
    }

    override fun onClickGameDetail(game: Game) {
        view.showGameDetailUI(game)
    }
}