package com.spotifyclone.presentation

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.forEach
import kotlinx.android.synthetic.main.activity_main.*
import com.spotifyclone.R
import com.spotifyclone.SpotifyApplication
import com.spotifyclone.presentation.base.BaseActivity
import com.spotifyclone.presentation.base.ToolbarParameters
import com.spotifyclone.presentation.login.LoginActivity
import com.spotifyclone.presentation.main.PageTabAdapter
import com.spotifyclone.presentation.music.MusicPlayerActivity
import com.spotifyclone.presentation.music.MusicPlayerFragment
import com.spotifyclone.tools.musicplayer.PlaylistMusicPlayer
import com.spotifyclone.tools.session.UserSession
import kotlinx.android.synthetic.main.include_bottom_navigation_menu.*


class MainActivity : BaseActivity() {

    private val playlistMusicPlayer = PlaylistMusicPlayer.getInstance(this@MainActivity)
    private lateinit var tabAdapter: PageTabAdapter
    val context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.setContentView(R.layout.activity_main)

        super.setupToolbar(ToolbarParameters())

        if (UserSession.getUserStatus() != UserSession.USER_LOGGED) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }

        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        if (!tabAdapter.onBackPressed()) {
            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.cancel(1)
            super.onBackPressed()
        }
    }

    override fun initComponents() {
        createTabsMainView()
        createMusicPlayerFragment()
    }

    private fun createTabsMainView() {
        tabAdapter = PageTabAdapter(this, containerViewPager)
        containerViewPager.adapter = tabAdapter
        containerViewPager.isUserInputEnabled = false
        getString(R.string.dialog_alert_btn_permissions_cancel)

        bottomNavMenu.menu.forEach {
            val view = bottomNavMenu.findViewById<View>(it.itemId)
            view.setOnLongClickListener { true }
        }
        bottomNavMenu.setOnNavigationItemSelectedListener { item: MenuItem ->
            tabAdapter.selectTab(item)
        }
    }

    private fun createMusicPlayerFragment() {
        val fragmentManager = supportFragmentManager
        val musicPlayerFragment =
            MusicPlayerFragment.getInstanceFragment(this)
        fragmentManager.beginTransaction()
            .add(R.id.containerMusicPlayer, musicPlayerFragment)
            .commit()

    }

    fun onclickFragmentMusicPlayer(view: View) {
        if (view.id == R.id.fragmentMusicPlayer) {
            val music = playlistMusicPlayer.getCurrentMusic()
            music?.albumUriId?.let {
                val intent = MusicPlayerActivity.getIntent(
                    context,
                    music.title,
                    music.artist,
                    music.albumUriId,
                    getString(R.string.fragment_local_songs_title)
                )
                this@MainActivity.startActivity(
                    intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                )
            }
        }
    }
}
