package com.spotifyclone.presentation.music

import android.os.Bundle
import android.text.SpannedString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.spotifyclone.R
import com.spotifyclone.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.item_fragment_music_player.*

class ItemMusicPlayerFragment(private val onclickCallback: () -> Unit): BaseFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        onclickCallback.invoke()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_fragment_music_player, container, false)
    }

    override fun initComponents() {
        val label: SpannedString = buildSpannedString {
            append(requireArguments().getString(EXTRA_MUSIC_TITLE))
            color(ContextCompat.getColor(requireContext(), R.color.lightGray)) {
                append(" • ${requireArguments().getString(EXTRA_MUSIC_ARTIST)}")
            }
        }
        txtMusicLabel.text = label
        itemFragmentMusic.setOnClickListener(this)
    }

    companion object {
        private const val EXTRA_MUSIC_TITLE = "EXTRA_MUSIC_TITLE"
        private const val EXTRA_MUSIC_ARTIST = "EXTRA_MUSIC_ARTIST"

        fun getInstance(
            musicTitle: String,
            musicArtist: String,
            onclickCallback: () -> Unit
        ): ItemMusicPlayerFragment {
            val fragment = ItemMusicPlayerFragment(onclickCallback)
            val bundle = Bundle()
            bundle.putString(EXTRA_MUSIC_TITLE, musicTitle)
            bundle.putString(EXTRA_MUSIC_ARTIST, musicArtist)
            fragment.arguments = bundle
            return fragment
        }
    }
}