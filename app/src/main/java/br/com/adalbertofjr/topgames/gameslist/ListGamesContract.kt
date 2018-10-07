package br.com.adalbertofjr.topgames.gameslist

import br.com.adalbertofjr.topgames.data.api.model.Game

/**
 * ListGamesContract
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
interface ListGamesContract {

    interface View {

        fun showLoading(show: Boolean)

        fun showGames(games: List<Game>)

        fun showGameDetailUI(game: Game)
    }

    interface Presenter {

        fun loadGames()

        fun onClickGameDetail(game: Game)
    }
}