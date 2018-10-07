package br.com.adalbertofjr.topgames.data.api

import br.com.adalbertofjr.topgames.gameslist.ListGamesActivity
import dagger.Subcomponent

/**
 * TwitchApiComponent
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */

@Subcomponent(modules = arrayOf(TwitchApiModule::class))
interface TwitchApiComponent {
    fun inject(target: ListGamesActivity)
}
