package br.com.adalbertofjr.topgames.root

import br.com.adalbertofjr.topgames.data.api.TwitchApiModule
import br.com.adalbertofjr.topgames.gamedetail.DetailGameComponent
import br.com.adalbertofjr.topgames.gamedetail.DetailGameModule
import br.com.adalbertofjr.topgames.gameslist.ListGameComponent
import br.com.adalbertofjr.topgames.gameslist.ListGameModule
import dagger.Component
import javax.inject.Singleton

/**
 * ApplicationComponent
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, TwitchApiModule::class))
interface ApplicationComponent {
    fun inject(target: App)
    fun inject(target: ListGameModule): ListGameComponent
    fun inject(target: DetailGameModule): DetailGameComponent
}