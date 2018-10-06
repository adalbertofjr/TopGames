package br.com.adalbertofjr.topgames.gameslist

/**
 * ListGamesPresenter
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class ListGamesPresenter(val view: ListGamesContract.View) : ListGamesContract.Presenter {


    override fun loadGames() {

        val games = listOf<Game>(
                Game("Counter Strike"),
                Game("Assassins Creed Odyssey"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("South Park : Stick of Truth"),
                Game("Street Of Rage 4")
        )

        if (this.view != null) {
            this.view.showLoading(true)
            this.view.showGames(games)
            this.view.showLoading(false)
        }
    }

    override fun onClickGameDetail() {
        this.view.let { it.showGameDetailUI() }
    }

    data class Game(val name: String)
}