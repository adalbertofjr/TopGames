package br.com.adalbertofjr.topgames.gameslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.adalbertofjr.topgames.R
import kotlinx.android.synthetic.main.item_list_games.view.*

class ListGamesAdapter(val games: ArrayList<ListGamesPresenter.Game>) : RecyclerView.Adapter<ListGamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_games, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder != null) {
            val game = games.get(0)

            with(holder) {
                card.txv_title.text = game.name
            }
        }
    }

    class ViewHolder(val card: View) : RecyclerView.ViewHolder(card)
}
