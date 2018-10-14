package br.com.adalbertofjr.topgames.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.COLUMN_BOX
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.COLUMN_CHANNELS
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.COLUMN_LOGO
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.COLUMN_NAME
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.COLUMN_VIEWERS
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.TABLE_NAME
import br.com.adalbertofjr.topgames.util.Constants.Companion.name
import br.com.adalbertofjr.topgames.util.Constants.Companion.version

/**
 * TopGamesDbHelper
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class TopGamesDbHelper(context: Context) : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val SQL_CREATE_TOP_GAMES_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_BOX + " TEXT NOT NULL, " +
                COLUMN_LOGO + " TEXT NOT NULL, " +
                COLUMN_CHANNELS + " INTEGER NOT NULL, " +
                COLUMN_VIEWERS + " INTEGER NOT NULL )"

        db?.execSQL(SQL_CREATE_TOP_GAMES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP DATABASE IF EXISTS " + TABLE_NAME)
    }
}


