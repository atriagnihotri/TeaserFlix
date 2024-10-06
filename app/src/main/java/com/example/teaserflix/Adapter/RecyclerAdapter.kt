package com.example.teaserflix.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teaserflix.Activity.DetailView
import com.example.teaserflix.Models.Videos
import com.example.teaserflix.R

class RecyclerAdapter(private var movies: List<Videos>, private var context: Context): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageBanner: ImageView = itemView.findViewById(R.id.imagebanner)
        private val textTitle: TextView = itemView.findViewById(R.id.title)
        fun bind(movie: Videos) {
            textTitle.text = movie.title

            var imgurl:String="http://192.168.43.249:8000"+movie.banner
            Glide.with(itemView.context)
                .load(imgurl)
                .into(imageBanner)




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.bannerview, parent, false)
        return ViewHolder(view)
    }
    fun updateData(newData: List<Videos>, context:Context) {
        movies = newData
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            var imgurl:String="http://192.168.43.249:8000"+movie.banner

            var intent=Intent(context, DetailView::class.java).apply {
                putExtra("trailer", movie.trailer)
                putExtra("banner", imgurl)
                putExtra("title", movie.title)
                putExtra("description", movie.description)
                putExtra("language", movie.language.language)
                putExtra("category", movie.category.category)

            }
            context.startActivity(intent)

        }


    }
}