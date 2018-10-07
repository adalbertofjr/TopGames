package br.com.adalbertofjr.topgames.root

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * ApplicationModule
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
@Module
class ApplicationModule(val application: App) {

    @Provides
    @Singleton
    fun provideApp() = application

}