package com.example.teaserflix.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.teaserflix.Adapter.RecyclerAdapter
import com.example.teaserflix.Models.MovieResponse
import com.example.teaserflix.R
import com.example.teaserflix.Retrofit.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Home : Fragment() {
    lateinit var recycler1: RecyclerView
    lateinit var recycler2: RecyclerView
    lateinit var recycler3: RecyclerView
    lateinit var recycler4: RecyclerView
    lateinit var recycler5: RecyclerView


    lateinit var adapter1: RecyclerAdapter
    lateinit var adapter2: RecyclerAdapter
    lateinit var adapter3: RecyclerAdapter
    lateinit var adapter4: RecyclerAdapter
    lateinit var adapter5: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val imageList = ArrayList<SlideModel>()


        imageList.add(SlideModel("http://192.168.43.249:8000/media/image/emregency_poster.jpg", "Emergency"))
        imageList.add(SlideModel("http://192.168.43.249:8000/media/image/joker.jpg", "Joker"))
        imageList.add(SlideModel("http://192.168.43.249:8000/media/image/tumbbad_poster.jpg", "Tumbbad"))
        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
//        imageSlider.setItemClickListener(object : ItemClickListener {
//            override fun onItemSelected(position: Int) {
//            }
//            override fun doubleClick(position: Int) {
//
//            } })




        recycler1 = view.findViewById(R.id.recycler1)
        recycler2 = view.findViewById(R.id.recycler2)
        recycler3 = view.findViewById(R.id.recycler3)
        recycler4 = view.findViewById(R.id.recycler4)
        recycler5 = view.findViewById(R.id.recycler5)




        recycler1.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        recycler2.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        recycler3.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        recycler4.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        recycler5.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }


        adapter1 = RecyclerAdapter(emptyList(),requireContext())
        adapter2 = RecyclerAdapter(emptyList(),requireContext())
        adapter3 = RecyclerAdapter(emptyList(),requireContext())
        adapter4 = RecyclerAdapter(emptyList(),requireContext())
        adapter5 = RecyclerAdapter(emptyList(),requireContext())



        recycler1.adapter = adapter1
        recycler2.adapter = adapter2
        recycler3.adapter = adapter3
        recycler4.adapter = adapter4
        recycler5.adapter = adapter5



        data("action", adapter1, recycler1)
        data("marvel", adapter2, recycler2)
        data("thriller", adapter3, recycler3)
        data("bollywood", adapter4, recycler4)
        data("hollywood", adapter5, recycler5)




        return view
    }

    fun data(category: String, adapter: RecyclerAdapter, recyclerView: RecyclerView) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.249:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofit.getTrailersByCategory(category)
        retrofitData.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                val dataResponse = response.body()
                dataResponse?.let {
                    adapter.updateData(it.data,requireContext())
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                val error = t.message
                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
