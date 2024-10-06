package com.example.teaserflix.Activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.teaserflix.Models.Favorite
import com.example.teaserflix.R
import com.example.teaserflix.Retrofit.ApiInterface
import com.example.teaserflix.download.DownloadService
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailView : AppCompatActivity() {
    lateinit var titles:TextView
    lateinit var dtitles:TextView
    lateinit var dlang:TextView
    lateinit var dgenre:TextView
    lateinit var descriptions:TextView
    lateinit var banners:ImageView
    lateinit var save:ImageView
    lateinit var play: Button
    lateinit var share:ImageView
    lateinit var like:ImageView
    lateinit var dislike:ImageView
    lateinit var download:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)
        titles=findViewById(R.id.title)
        descriptions=findViewById(R.id.description)
        banners=findViewById(R.id.bannerimg)
        play=findViewById(R.id.Play)
        dtitles=findViewById(R.id.dtitle)
        dlang=findViewById(R.id.dlan)
        dgenre=findViewById(R.id.dgenre)
        save=findViewById(R.id.save)
        share=findViewById(R.id.share)
        like=findViewById(R.id.like)
        dislike=findViewById(R.id.dislike)
        download=findViewById(R.id.download)






        val trailer = intent.getStringExtra("trailer")
        val title = intent.getStringExtra("title")
        val banner = intent.getStringExtra("banner")
        val description = intent.getStringExtra("description")
        val language = intent.getStringExtra("language")
        val category = intent.getStringExtra("category")
        dtitles.text=title
        dlang.text=language
        dgenre.text=category

        titles.text=title
        descriptions.text=description

        Glide.with(this)
            .load(banner)
            .into(banners)
        play.setOnClickListener {
        val url:String="http://192.168.43.249:8000"+trailer.toString()
            val intent=Intent(this, Player::class.java)
            intent.putExtra("trailer",url)
            startActivity(intent)
        }

        save.setOnClickListener {
            save.setBackgroundColor(Color.RED)
            val pref=getSharedPreferences("mypref", MODE_PRIVATE)
            val user=pref.getString("User","Atri").toString()
            val data= Favorite(Video=banner.toString(),User=user)
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.43.249:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
            val retrofitData = retrofit.favorite(data)
            retrofitData.enqueue(object : Callback<Favorite?> {
                override fun onResponse(
                    call: Call<Favorite?>,
                    response: Response<Favorite?>
                ) {
                    val dataResponse = response.body()
                    if (dataResponse != null) {
                        val title:String= title.toString()
                        Toasty.info(applicationContext, "$title Saved", Toast.LENGTH_SHORT, true).show();

                    }
                }

                override fun onFailure(call: Call<Favorite?>, t: Throwable) {
                    Toasty.error(applicationContext, "Error", Toast.LENGTH_SHORT, true).show();
                }
            })
        }

        share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "http://192.168.43.249:8000"+trailer.toString())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }

        like.setOnClickListener {
            like.setBackgroundColor(Color.RED)
            dislike.setBackgroundColor(Color.GRAY)

        }
        dislike.setOnClickListener {
            dislike.setBackgroundColor(Color.RED)
            like.setBackgroundColor(Color.GRAY)

        }

        download.setOnClickListener {
            val downloader= DownloadService(applicationContext)
            downloader.downloadfile("http://192.168.43.249:8000"+trailer.toString())

        }





    }
}