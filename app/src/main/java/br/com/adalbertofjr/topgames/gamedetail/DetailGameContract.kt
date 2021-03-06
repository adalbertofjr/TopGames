package br.com.adalbertofjr.topgames.gamedetail

import br.com.adalbertofjr.topgames.data.api.model.Game

/**
 * DetailGameContract
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
interface DetailGameContract {

    interface View {
        fun showDetailGame(game: Game)
    }

    interface Presenter {
        fun loadDataGame(game: Game)
    }
}