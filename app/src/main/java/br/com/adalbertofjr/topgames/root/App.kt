package br.com.adalbertofjr.topgames.root

import android.app.Application
import br.com.adalbertofjr.topgames.data.api.TwitchApiModule

/**
 * App
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class App : Application() {
    val component: ApplicationComponent by lazy {

        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .twitchApiModule(TwitchApiModule(this))
                .build()
    }


    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}