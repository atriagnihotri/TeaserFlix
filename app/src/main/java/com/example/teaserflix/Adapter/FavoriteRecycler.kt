package com.example.teaserflix.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teaserflix.R

class FavoriteRecycler(private var movies: ArrayList<String>, private var context: Context): RecyclerView.Adapter<FavoriteRecycler.RecyclerViewHolder>() {
    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageBanner: ImageView = itemView.findViewById(R.id.imagebanner)
        fun bind(movie: String) {


            Glide.with(itemView.context)
                .load(movie)
                .into(imageBanner)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favview, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)

    }
}