package br.com.adalbertofjr.topgames.gameslist

/**
 * ListGamesContract
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
interface ListGamesContract {

    interface View {

        fun showLoading(show: Boolean)

        fun showGames(games: List<ListGamesPresenter.Game>)

        fun showGameDetailUI(game: ListGamesPresenter.Game)
    }

    interface Presenter {

        fun loadGames()

        fun onClickGameDetail(game: ListGamesPresenter.Game)
    }
}