package br.com.adalbertofjr.topgames.gameslist

import br.com.adalbertofjr.topgames.data.api.TwitchAPI
import br.com.adalbertofjr.topgames.data.api.model.Game
import dagger.Module
import dagger.Provides

/**
 * ListGamePresenterModule
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */

@Module
class ListGameModule(val activity: ListGamesActivity) {

    @Provides
    fun provideListGamesPresenter(twitchApi: TwitchAPI): ListGamesPresenter {
        return ListGamesPresenter(twitchApi, activity)
    }

    @Provides
    fun providesListGamesAdapter(): ListGamesAdapter {
        return ListGamesAdapter(providesGameList())
    }

    @Provides
    fun providesGameList() : ArrayList<Game>{
        return ArrayList<Game>()
    }
}