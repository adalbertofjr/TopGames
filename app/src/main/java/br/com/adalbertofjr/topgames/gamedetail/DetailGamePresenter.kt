package br.com.adalbertofjr.topgames.gamedetail

import br.com.adalbertofjr.topgames.data.api.model.Game

/**
 * DetailGamePresenter
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class DetailGamePresenter : DetailGameContract.Presenter {

    private lateinit var view: DetailGameContract.View

    override fun loadDataGame(game: Game) {
        view.showDetailGame(game)
    }

    fun setView(view: DetailGameContract.View) {
        this.view = view
    }
}