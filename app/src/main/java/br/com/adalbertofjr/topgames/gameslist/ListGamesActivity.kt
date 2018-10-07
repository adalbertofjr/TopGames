package br.com.adalbertofjr.topgames.gameslist

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import br.com.adalbertofjr.topgames.R
import br.com.adalbertofjr.topgames.data.api.TwitchAPI
import br.com.adalbertofjr.topgames.data.api.TwitchApiModule
import br.com.adalbertofjr.topgames.data.api.model.TwitchData
import br.com.adalbertofjr.topgames.gamedetail.DetailGameActivity
import br.com.adalbertofjr.topgames.gameslist.ListGamesPresenter.Game
import br.com.adalbertofjr.topgames.root.App
import br.com.adalbertofjr.topgames.util.Constants.Companion.DETAIL_EXTRA
import kotlinx.android.synthetic.main.activity_list_games.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * ListGamesActivity
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class ListGamesActivity : AppCompatActivity(), ListGamesContract.View, ListGamesAdapter.ListGameAdapterCallback {


    val LOG_TAG = ListGamesActivity::class.java.simpleName

    private lateinit var presenter: ListGamesPresenter
    private var gamesList = ArrayList<Game>()
    private var adapter: ListGamesAdapter? = null

    val Activity.app: App
        get() = application as App


    val component by lazy { app.component.inject(TwitchApiModule(this)) }

    @Inject
    lateinit var twitchAPI: TwitchAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_games)

        toolbar.setTitle(getString(R.string.app_name))
        toolbar.setTitleTextColor(Color.WHITE)

        presenter = ListGamesPresenter(this)

        gamesList = ArrayList()
        adapter = ListGamesAdapter(gamesList, this)

        rv_game_list.setHasFixedSize(true)
        rv_game_list.layoutManager = GridLayoutManager(this, 3)
        rv_game_list.adapter = adapter


        component.inject(this)

        val twitchCall = twitchAPI.getTopGames("0t4py0qo1iqagd5dnig9bheol9yo22")

        twitchCall.enqueue(object : Callback<TwitchData> {
            override fun onResponse(call: Call<TwitchData>, response: Response<TwitchData>) {
                val topGames = response.body()!!.top

                for (game in topGames) {
                    Log.i("Jogo: ", game.toString())
                }
            }

            override fun onFailure(call: Call<TwitchData>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    override fun onStart() {
        super.onStart()

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