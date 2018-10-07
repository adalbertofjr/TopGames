package br.com.adalbertofjr.topgames.gamedetail

import br.com.adalbertofjr.topgames.gameslist.ListGamesPresenter

/**
 * DetailGamePresenter
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class DetailGamePresenter(val view: DetailGameContract.View) : DetailGameContract.Presenter {

    override fun loadDataGame(game: ListGamesPresenter.Game) {
        if (this.view != null) {
            this.view.showDetailGame(game)
        }
    }
}