package br.com.adalbertofjr.topgames.gameslist

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import br.com.adalbertofjr.topgames.R
import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.gamedetail.DetailGameActivity
import br.com.adalbertofjr.topgames.root.App
import br.com.adalbertofjr.topgames.util.Constants.Companion.DETAIL_EXTRA
import kotlinx.android.synthetic.main.activity_list_games.*
import javax.inject.Inject

/**
 * ListGamesActivity
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class ListGamesActivity : AppCompatActivity(), ListGamesContract.View, ListGamesAdapter.ListGameAdapterCallback {
    val LOG_TAG = ListGamesActivity::class.java.simpleName

    private var gamesList = ArrayList<Game>()
    private var adapter: ListGamesAdapter? = null

    val Activity.app: App
        get() = application as App

    val component by lazy { app.component.inject(ListGameModule(this)) }

    @Inject
    lateinit var presenter: ListGamesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_games)

        component.inject(this)

        //Presenter
        presenter.view = this

        toolbar.setTitle(getString(R.string.app_name))
        toolbar.setTitleTextColor(Color.WHITE)

        gamesList = ArrayList()
        adapter = ListGamesAdapter(gamesList, this)

        rv_game_list.setHasFixedSize(true)
        rv_game_list.layoutManager = GridLayoutManager(this, 3)
        rv_game_list.adapter = adapter

        presenter.loadGames()
    }

    override fun onGameClickListener(game: Game) {
        presenter.onClickGameDetail(game)
    }

    override fun showLoading(show: Boolean) {
        Log.d(LOG_TAG, "showLoading: ${show}")
    }

    override fun showGames(games: List<Game>) {
        Log.d(LOG_TAG, "showGames")

        gamesList.addAll(games)
        adapter?.let { it.notifyDataSetChanged() }
    }

    override fun showGameDetailUI(game: Game) {
        Log.d(LOG_TAG, "showGameDetailUI: ${game.name}")

        val intent = Intent(this, DetailGameActivity::class.java)
        intent.putExtra(DETAIL_EXTRA, game)

        startActivity(intent)
    }
}