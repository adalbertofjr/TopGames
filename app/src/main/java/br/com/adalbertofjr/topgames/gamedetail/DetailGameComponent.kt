package br.com.adalbertofjr.topgames.gamedetail

import dagger.Subcomponent

/**
 * DetailGameComponent
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */
@Subcomponent(modules = arrayOf(DetailGameModule::class))
interface DetailGameComponent {
    fun inject(target: DetailGameActivity)
}
