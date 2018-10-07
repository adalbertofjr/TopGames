package br.com.adalbertofjr.topgames.gamedetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.adalbertofjr.topgames.R
import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.util.Constants
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * DetailGameActivity
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
class DetailGameActivity : AppCompatActivity(), DetailGameContract.View {

    var presenter: DetailGamePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        setSupportActionBar(tb_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        presenter = DetailGamePresenter(this)
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