package br.com.adalbertofjr.topgames.gamedetail

import br.com.adalbertofjr.topgames.data.api.model.Game

/**
 * DetailGamePresenter
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class DetailGamePresenter : DetailGameContract.Presenter {

    var view: DetailGameContract.View? = null

    override fun loadDataGame(game: Game) {
        view?.let {
            it.showDetailGame(game)
        }
    }
}