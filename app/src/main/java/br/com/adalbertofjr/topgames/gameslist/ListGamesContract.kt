package br.com.adalbertofjr.topgames.gameslist

import android.content.Context
import br.com.adalbertofjr.topgames.data.api.model.Game

/**
 * ListGamesContract
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
interface ListGamesContract {

    interface View {

        fun showLoading(show: Boolean, refresh: Boolean)

        fun showGames(games: List<Game>)

        fun showGameDetailUI(game: Game)

        fun showErrorConnection(show: Boolean)

        fun showSnackBar(msg : String)
    }

    interface Presenter {

        fun setView(view: View)

        fun loadGames(refresh: Boolean)

        fun onClickGameDetail(game: Game)

        fun onRefresh()

        fun checkConnection(context: Context) : Boolean
    }
}