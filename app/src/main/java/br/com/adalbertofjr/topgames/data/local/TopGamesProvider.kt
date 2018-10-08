package br.com.adalbertofjr.topgames.data.local

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.CONTENT_TYPE
import br.com.adalbertofjr.topgames.data.local.TopGamesContract.TopGamesEntry.Companion.TABLE_NAME
import br.com.adalbertofjr.topgames.util.Constants.Companion.CONTENT_AUTHORITY
import br.com.adalbertofjr.topgames.util.Constants.Companion.PATH_TOP_GAMES

/**
 * TopGamesProvider
 * Created by Adalberto Fernandes Júnior on 07/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class TopGamesProvider : ContentProvider() {

    private val TOP_GAMES = 1

    // The URI Matcher used by this content provider.
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOP_GAMES, TOP_GAMES)
    }

    private var mOpenHelper: SQLiteOpenHelper? = null

//    fun buildUriMatcher(): UriMatcher {
//        val uriMatcherMatcher = UriMatcher(UriMatcher.NO_MATCH)
//        var authority = CONTENT_AUTHORITY
//
//        uriMatcherMatcher.addURI(authority, PATH_TOP_GAMES, TOP_GAMES)
//
//        return uriMatcherMatcher
//    }

    override fun onCreate(): Boolean {
        mOpenHelper = TopGamesDbHelper(context)

        return true
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val match = sUriMatcher.match(uri)
        var retCursor: Cursor? = null

        when (match) {
            TOP_GAMES -> run {
                retCursor = mOpenHelper?.getWritableDatabase()?.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder
                )
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        retCursor?.setNotificationUri(context!!.contentResolver, uri)

        return retCursor!!

    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        val db = mOpenHelper!!.writableDatabase
        val match = sUriMatcher.match(uri)
        val returnUri: Uri

        when (match) {
            TOP_GAMES -> {
                val _id = db?.insert(TABLE_NAME, null, values)
                if (_id != null && _id > 0)
                    returnUri = TopGamesContract.TopGamesEntry.buildPTopGamesUri(_id.toLong())
                else
                    throw android.database.SQLException("Failed to insert row into $uri")
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return returnUri
    }


    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        var selecao = selection
        val db = mOpenHelper?.getWritableDatabase()!!
        val match = sUriMatcher.match(uri)
        val rowsDeleted: Int

        if (selecao == null) {
            selecao = "1"
        }

        when (match) {
            TOP_GAMES -> {
                rowsDeleted = db.delete(TABLE_NAME, selecao, selectionArgs)
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        if (rowsDeleted != 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }

        return rowsDeleted
    }

    override fun getType(uri: Uri?): String {
        val match = sUriMatcher.match(uri)
        when (match) {
            TOP_GAMES -> {
                return CONTENT_TYPE
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
    }

    override fun bulkInsert(uri: Uri?, values: Array<out ContentValues>?): Int {
        val db = mOpenHelper?.getWritableDatabase()!!
        val match = sUriMatcher.match(uri)
        var returnCount: Int

        when (match) {
            TOP_GAMES -> {
                db.beginTransaction()
                returnCount = 0
                try {

                    values?.forEach {
                        val _id = db.insert(TABLE_NAME, null, it)!!
                        if (_id != null && _id != -1L) {
                            returnCount++
                        }
                    }

                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
                context!!.contentResolver.notifyChange(uri, null)
                return returnCount
            }
            else -> return super.bulkInsert(uri, values)
        }
    }
}