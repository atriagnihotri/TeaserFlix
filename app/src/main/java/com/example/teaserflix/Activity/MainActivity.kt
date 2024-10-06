package com.example.teaserflix.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.example.teaserflix.Fragments.Favorite
import com.example.teaserflix.Fragments.Home
import com.example.teaserflix.Fragments.Profile
import com.example.teaserflix.R
import com.example.teaserflix.Fragments.Search
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var frame:FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(Home())
        frame=findViewById(R.id.container)
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView

        bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.home) {
                loadFragment(Home())
                true
            } else if (item.itemId == R.id.Search) {
                loadFragment(Search())
                true
            } else if (item.itemId == R.id.Save) {
                loadFragment(Favorite())
                true
            } else if (item.itemId == R.id.Profile) {
                loadFragment(Profile())
                true
            } else {
                false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

}
