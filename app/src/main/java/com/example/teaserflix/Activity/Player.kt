package com.example.teaserflix.Activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.teaserflix.R

class Player : AppCompatActivity() {
    lateinit var video: PlayerView
    lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        video = findViewById(R.id.videoview)


        hide()
        val data = intent.getStringExtra("trailer")
        player = ExoPlayer.Builder(applicationContext).build()
        video.player = player
        val mediaItem = MediaItem.fromUri(data.toString())
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onBackPressed() {
        player.stop()
        super.onBackPressed()
    }

    fun hide() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(android.R.id.content)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())

            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        }
    }

    override fun onPause() {
        player.stop()
        super.onPause()
    }
}