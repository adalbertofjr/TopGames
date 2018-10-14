package br.com.adalbertofjr.topgames.util

import android.net.Uri

/**
 * Constants
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */

class Constants {

    companion object {
        val DETAIL_EXTRA = "detail_extra"

        //API
        val API_KEY = "0t4py0qo1iqagd5dnig9bheol9yo22"

        //DB
        val CONTENT_AUTHORITY = "br.com.adalbertofjr.topgames"
        var BASE_CONTENT_URI = Uri.parse("content://$CONTENT_AUTHORITY")
        val PATH_TOP_GAMES = "top_games"

        // DB Information
        val version: Int = 1
        val name: String? = "top_games_twitch.db"
    }
}