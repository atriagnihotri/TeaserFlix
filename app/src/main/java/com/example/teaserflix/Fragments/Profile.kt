package com.example.teaserflix.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teaserflix.Activity.Login
import com.example.teaserflix.R
import es.dmoral.toasty.Toasty


class Profile : Fragment() {

 lateinit var name:TextView
 lateinit var logout:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

        name=view.findViewById(R.id.name)
        logout=view.findViewById(R.id.logout)

        val pref=requireContext().getSharedPreferences("mypref", AppCompatActivity.MODE_PRIVATE)
        val user=pref.getString("User","Atri").toString()
        name.text=user.toUpperCase()
        logout.setOnClickListener {
            Toasty.success(requireContext(), "Logging Out", Toast.LENGTH_SHORT, true).show();
            val editor = pref.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(requireContext(),Login::class.java))
            activity?.finishAffinity()

        }

        return view
    }


}