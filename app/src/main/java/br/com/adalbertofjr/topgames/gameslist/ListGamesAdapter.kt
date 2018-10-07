package br.com.adalbertofjr.topgames.gameslist

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.adalbertofjr.topgames.R
import br.com.adalbertofjr.topgames.data.api.model.Game
import kotlinx.android.synthetic.main.item_list_games.view.*

class ListGamesAdapter(val games: List<Game>, val listener: ListGameAdapterCallback) : RecyclerView.Adapter<ListGamesAdapter.ViewHolder>() {
    private val LOG_TAG = ListGamesAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_games, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return games.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder != null) {
            val game = games.get(position)

            holder.card.txv_title.text = game.name

            holder.card.setOnClickListener { v ->
                Log.i(LOG_TAG, game.name)

                if (listener != null) {
                    listener.onGameClickListener(game)
                }
            }
        }
    }

    class ViewHolder(val card: View) : RecyclerView.ViewHolder(card)

    public interface ListGameAdapterCallback {
        fun onGameClickListener(game: Game)
    }
}
