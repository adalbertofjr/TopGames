package br.com.adalbertofjr.topgames.data.local

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import br.com.adalbertofjr.topgames.util.Constants.Companion.BASE_CONTENT_URI
import br.com.adalbertofjr.topgames.util.Constants.Companion.CONTENT_AUTHORITY
import br.com.adalbertofjr.topgames.util.Constants.Companion.PATH_TOP_GAMES

/**
 * TopGamesContract
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class TopGamesContract {

    class TopGamesEntry : BaseColumns {

        companion object {
            // Content provider
            var CONTENT_URI =
                    BASE_CONTENT_URI
                            .buildUpon()
                            .appendPath(PATH_TOP_GAMES).build()

            val CONTENT_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOP_GAMES

            val CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOP_GAMES

            //Top Gams table db
            val TABLE_NAME = "top_games"
            val COLUMN_NAME = "name"
            val COLUMN_BOX = "box"
            val COLUMN_LOGO = "Logo"
            val COLUMN_CHANNELS = "channels"
            val COLUMN_VIEWERS = "viewers"

            fun buildTopGames(topGames: String): Uri {
                return CONTENT_URI.buildUpon().appendPath(topGames).build()
            }

            fun buildPTopGamesUri(id: Long): Uri {
                return ContentUris.withAppendedId(CONTENT_URI, id)
            }
        }
    }
}