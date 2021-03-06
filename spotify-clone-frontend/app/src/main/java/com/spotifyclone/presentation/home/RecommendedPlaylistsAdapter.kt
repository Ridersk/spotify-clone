package com.spotifyclone.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.spotifyclone.R
import com.spotifyclone.data.model.Playlist
import com.spotifyclone.tools.animations.ReducerAndRegain
import kotlinx.android.synthetic.main.item_recommended_playlist.view.*

class RecommendedPlaylistsAdapter(
    private val context: Context,
    private val playlists: List<Playlist>,
    private val onItemClickListener: ((playlist: Playlist) -> Unit)
) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        TODO("not implemented") //To change body of created functions
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return playlists.count()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolderItem
        val itemView: View

        if (convertView == null) {
            val inflater: LayoutInflater = LayoutInflater.from(parent?.context)
            itemView = inflater.inflate(R.layout.item_recommended_playlist, parent, false)

            viewHolder = ViewHolderItem(context, itemView, onItemClickListener)

            itemView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolderItem
            itemView = convertView
        }

        viewHolder.bindView(playlists[position])
        return itemView
    }

    class ViewHolderItem(
        private val context: Context,
        private  val itemView: View,
        private val onItemClickListener: (playlist: Playlist) -> Unit
    ) {
        private val title = itemView.textToolbarTitle


        fun bindView(playlist: Playlist) {
            title.text = playlist.title

            itemView.setOnClickListener {
                onItemClickListener.invoke(playlist)
            }

            itemView.setOnTouchListener { view, event ->
                ReducerAndRegain(context).onTouch(view, event)
            }
        }
    }
}