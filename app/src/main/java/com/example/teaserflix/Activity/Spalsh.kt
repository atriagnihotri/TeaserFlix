package com.example.teaserflix.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.teaserflix.R

class Spalsh : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            val pref=getSharedPreferences("mypref", MODE_PRIVATE)
            val bool=pref.getBoolean("access",false)
            if (bool){
                startActivity(Intent(this,MainActivity::class.java))
                finishAffinity()
            }
            else{
                startActivity(Intent(this, Login::class.java))
                finishAffinity()


            }

        }, 2000)
        hide()
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
}