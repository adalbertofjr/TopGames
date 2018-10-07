package br.com.adalbertofjr.topgames.gameslist

import dagger.Subcomponent

/**
 * ListGameComponent
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */
@Subcomponent(modules = arrayOf(ListGameModule::class))
interface ListGameComponent {
    fun inject(target: ListGamesActivity)
}