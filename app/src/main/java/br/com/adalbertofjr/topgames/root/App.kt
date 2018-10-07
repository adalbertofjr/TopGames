package br.com.adalbertofjr.topgames.root

import android.app.Application

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
                .build()
    }


    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}