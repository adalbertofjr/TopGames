package br.com.adalbertofjr.topgames.root

import br.com.adalbertofjr.topgames.data.api.TwitchApiComponent
import br.com.adalbertofjr.topgames.data.api.TwitchApiModule
import dagger.Component
import javax.inject.Singleton

/**
 * ApplicationComponent
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(target: App)
    fun inject(target: TwitchApiModule): TwitchApiComponent
}