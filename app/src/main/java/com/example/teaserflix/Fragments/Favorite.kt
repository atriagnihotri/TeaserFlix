package com.example.teaserflix.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teaserflix.Adapter.FavoriteRecycler
import com.example.teaserflix.Adapter.RecyclerAdapter
import com.example.teaserflix.Models.Favorite
import com.example.teaserflix.Models.Videos
import com.example.teaserflix.R
import com.example.teaserflix.Retrofit.ApiInterface
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Favorite : Fragment() {
      lateinit var adapter: FavoriteRecycler
      lateinit var recycler:RecyclerView
      lateinit var videos: ArrayList<String>
      lateinit var fav:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_favorite, container, false)
        fav=view.findViewById(R.id.name)
          videos=ArrayList<String>()
        recycler=view.findViewById(R.id.recycler)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        val pref=requireContext().getSharedPreferences("mypref", AppCompatActivity.MODE_PRIVATE)
        val users=pref.getString("User","Atri").toString()
        fav.text=users.toUpperCase()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.249:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofit.getfavorite()
        retrofitData.enqueue(object : Callback<List<Favorite>?> {
            override fun onResponse(
                call: Call<List<Favorite>?>,
                response: Response<List<Favorite>?>
            ) {
                let {
                    val filteredVideos = response.body()!!.filter { it.User == users }
                        .map { it.Video }
                    for (i in filteredVideos){
                        videos.add(i)
                    }
                    adapter = FavoriteRecycler(videos,requireContext())
                    recycler.adapter=adapter
                }

             }

            override fun onFailure(call: Call<List<Favorite>?>, t: Throwable) {
                Toasty.error(requireContext(), "No Favorite Added", Toast.LENGTH_SHORT, true).show();
            }
        })

        return view
    }


}