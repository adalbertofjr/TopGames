package br.com.adalbertofjr.topgames.gamedetail

import dagger.Module
import dagger.Provides

/**
 * DetailGameModule
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */

@Module
class DetailGameModule {

    @Provides
    fun provideDetailGameresenter(): DetailGamePresenter {
        return DetailGamePresenter()
    }
}