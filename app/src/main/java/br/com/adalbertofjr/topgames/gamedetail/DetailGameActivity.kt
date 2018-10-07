package br.com.adalbertofjr.topgames.gamedetail

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.adalbertofjr.topgames.R
import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.root.App
import br.com.adalbertofjr.topgames.util.Constants
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

        // Inject
        component.inject(this)

        setSupportActionBar(tb_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        // View
        presenter.view = this
    }

    override fun onResume() {
        super.onResume()

        val game = this.intent.getParcelableExtra(Constants.DETAIL_EXTRA) as Game
        if (game != null) {
            this.presenter?.loadDataGame(game)
        }
    }

    override fun showDetailGame(game: Game) {
        if (game != null) {
            txv_title.text = game.name
        }
    }
}