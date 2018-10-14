package br.com.adalbertofjr.topgames.gameslist

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Uri
import br.com.adalbertofjr.topgames.data.api.TwitchAPI
import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.data.repository.RepositoryResponseListerner
import br.com.adalbertofjr.topgames.data.repository.TopMoviesRepository
import br.com.adalbertofjr.topgames.gameslist.domain.model.TopGames
import br.com.adalbertofjr.topgames.util.NetworkUtil

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

    override fun loadGames(refresh: Boolean) {
        // Connection
        if (!checkConnection()) {
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

    override fun checkConnection(): Boolean {
        return NetworkUtil().isConnected(activity.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)
    }

    override fun onRefresh() {
        offset = null
        games.clear()

        limparCache()

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

        val limit =
                if (limitToFetchGames + gameSize > limitOfGames)
                    limitOfGames - gameSize else limitToFetchGames

        TopMoviesRepository().getMoviesFromNetwork(
                twitchApi,
                limit,
                offset,
                object : RepositoryResponseListerner<TopGames> {
                    override fun onSuccess(topGames: TopGames) {
                        view.showLoading(false, refresh)
                        val links = topGames.links
                        offset = Uri.parse(links.next).getQueryParameter("offset").toInt()

                        topGames.games.forEach {
                            games.add(it)
                        }

                        TopMoviesRepository().insertMoviesInCache(topGames.games, activity)

                        view.showGames(games)
                    }

                    override fun onFailure(message: String) {
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

            TopMoviesRepository().getMoviesFromCache(
                    activity,
                    object : RepositoryResponseListerner<TopGames> {
                        override fun onSuccess(topGames: TopGames) {
                            topGames.games.forEach {
                                games.add(it)
                            }

                            view.showGames(games)
                            view.showLoading(false, refresh)
                        }

                        override fun onFailure(message: String) {
                            view.showLoading(false, refresh)
                            view.showErrorConnection(true)
                        }
                    }
            )
        }
    }

    private fun limparCache() {
        if (checkConnection()) {
            TopMoviesRepository().deleteMoviesFromCache(activity)
        }
    }
}