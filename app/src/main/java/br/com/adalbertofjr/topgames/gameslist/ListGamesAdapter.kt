package br.com.adalbertofjr.topgames.gameslist

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.adalbertofjr.topgames.R
import br.com.adalbertofjr.topgames.data.api.model.Game
import br.com.adalbertofjr.topgames.util.ImageUtil
import kotlinx.android.synthetic.main.item_list_games.view.*

class ListGamesAdapter(var games: List<Game>) : RecyclerView.Adapter<ListGamesAdapter.ViewHolder>() {

    private val LOG_TAG = ListGamesAdapter::class.java.simpleName
    private lateinit var listener: ListGameAdapterCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_games, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return games.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games.get(position)

        holder.card.txv_item_title.text = game.name
        ImageUtil().loadImage(fromUrl = game.box.large, toView = holder.card.imv_poster)

        holder.card.setOnClickListener { _ ->
            Log.i(LOG_TAG, game.name)
            if (listener != null) {
                listener.onGameClickListener(game)
            }
        }
    }

    fun setListerGameCallback(listener: ListGameAdapterCallback) {
        this.listener = listener
    }

    fun updateData(update: List<Game>) {
        games = update
        notifyDataSetChanged()
    }

    class ViewHolder(val card: View) : RecyclerView.ViewHolder(card)

    interface ListGameAdapterCallback {
        fun onGameClickListener(game: Game)
    }
}