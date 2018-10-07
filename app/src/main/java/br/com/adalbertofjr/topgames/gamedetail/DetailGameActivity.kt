package br.com.adalbertofjr.topgames.gamedetail

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.adalbertofjr.topgames.R
import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.root.App
import br.com.adalbertofjr.topgames.util.Constants
import br.com.adalbertofjr.topgames.util.loadImageFrom
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

/**
 * DetailGameActivity
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class DetailGameActivity : AppCompatActivity(), DetailGameContract.View {

    val Activity.app: App
        get() = application as App

    val component by lazy { app.component.inject(DetailGameModule()) }

    @Inject
    lateinit var presenter: DetailGamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        //Inject
        component.inject(this)

        //View
        presenter.setView(this)

        //Widgets
        setSupportActionBar(tb_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onResume() {
        super.onResume()

        val game = this.intent.getParcelableExtra(Constants.DETAIL_EXTRA) as Game
        this.presenter.loadDataGame(game)
    }

    override fun showDetailGame(game: Game) {
        imv_detail_poster_back.loadImageFrom(game.box.large)
        imv_poster_detail.loadImageFrom(game.box.large)
        txv_title.text = game.name
        txv_channel_visualiztions.text = "${game.channels} canais - ${game.viewers} visualizações"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}