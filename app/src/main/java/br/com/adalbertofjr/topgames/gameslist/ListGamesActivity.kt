package br.com.adalbertofjr.topgames.gameslist

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.util.Log
import android.view.View
import android.widget.AbsListView
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
    private val LOG_TAG = ListGamesActivity::class.java.simpleName
    private val Activity.app: App get() = application as App
    private val component by lazy { app.component.inject(ListGameModule(this)) }
    private lateinit var layoutManager: GridLayoutManager
    private var isScrolling = false

    @Inject
    lateinit var presenter: ListGamesPresenter

    @Inject
    lateinit var adapter: ListGamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_games)

        //Injection
        component.inject(this)

        //Presenter
        presenter.setView(this)

        //Callback
        adapter.setListerGameCallback(this)


        //Widgets
        toolbar.title = getString(R.string.app_name)
        toolbar.setTitleTextColor(Color.WHITE)

        rv_game_list.setHasFixedSize(true)
        layoutManager = GridLayoutManager(this, 3)
        rv_game_list.layoutManager = layoutManager
        rv_game_list.adapter = adapter
        rv_game_list.addOnScrollListener(this.onScrollListener())

        btn_tentar_novamente.setOnClickListener { presenter.onRefresh() }

        swp_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        swp_refresh.setOnRefreshListener { presenter.onRefresh() }

        //Carregar Games
        presenter.loadGames(true)
    }

    override fun onGameClickListener(game: Game) {
        presenter.onClickGameDetail(game)
    }

    override fun showLoading(show: Boolean, refresh: Boolean) {
        Log.d(this.LOG_TAG, "showLoading: $show")

        if (refresh) {
            swp_refresh.isRefreshing = show
        } else {
            pb_game_loading.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    override fun showGames(games: List<Game>) {
        Log.d(LOG_TAG, "showGames")
        adapter.updateData(games)
    }

    override fun showGameDetailUI(game: Game) {
        Log.d(LOG_TAG, "showGameDetailUI: ${game.name}")
        startDetailActivity(game)
    }

    override fun showErrorConnection(show: Boolean) {
        rl_error.visibility = if (show) View.VISIBLE else View.GONE
    }

    /**
     * Iniciar DetailGameActivity
     */
    private fun startDetailActivity(game: Game) {
        val intent = Intent(this, DetailGameActivity::class.java)
        intent.putExtra(DETAIL_EXTRA, game)
        startActivity(intent)
    }

    /**
     * Endless loading
     */
    private fun onScrollListener(): OnScrollListener {
        return object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var currentGames = 0
                var totalGames = 0
                var scrollOutItems = 0

                with(layoutManager) {
                    currentGames = childCount
                    totalGames = itemCount
                    scrollOutItems = this.findFirstVisibleItemPosition()
                }

                if (isScrolling && currentGames + scrollOutItems == totalGames && dy > 0) {
                    isScrolling = false
                    presenter.loadGames(false)
                }
            }
        }
    }
}