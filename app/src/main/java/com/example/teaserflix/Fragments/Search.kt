package com.example.teaserflix.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teaserflix.Adapter.RecyclerAdapter
import com.example.teaserflix.Models.MovieResponse
import com.example.teaserflix.Models.Videos
import com.example.teaserflix.R
import com.example.teaserflix.Retrofit.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale


class Search : Fragment() {
    lateinit var recycler: RecyclerView
    lateinit var search: SearchView
    lateinit var adapter:RecyclerAdapter
    lateinit var list:List<Videos>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        list=ArrayList<Videos>()

        val view= inflater.inflate(R.layout.fragment_search, container, false)
        recycler=view.findViewById(R.id.recycler)
        search=view.findViewById(R.id.search)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = RecyclerAdapter(emptyList(),requireContext())

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.249:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofit.getTrailers()
        retrofitData.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                val dataResponse = response.body()
                list=dataResponse!!.data

                dataResponse?.let {
                    adapter.updateData(it.data,requireContext())
                    recycler.adapter = adapter



                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                val error = t.message
                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        })

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                     filter(newText)
                return true
            }
        })


        return view
    }
fun filter(query:String?){
    if (query!=null){
        val filerlist=ArrayList<Videos>()
        for (i in list){
            if (i.title.toLowerCase(Locale.ROOT).contains(query)){
                filerlist.add(i)
            }
        }
        if (filerlist.isEmpty()){
            Toast.makeText(requireContext(), "No Data", Toast.LENGTH_SHORT).show()
        }
        else{
            adapter.updateData(filerlist,requireContext())
        }
    }
}

}