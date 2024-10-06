package com.example.teaserflix.Retrofit

import com.example.teaserflix.Models.Favorite
import com.example.teaserflix.Models.MovieResponse
import com.example.teaserflix.Models.Token
import com.example.teaserflix.Models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @GET("/trailers")
    fun getTrailersByCategory(@Query("category") category: String): Call<MovieResponse>

    @GET("/trailers")
    fun getTrailers(): Call<MovieResponse>



    //AUTHENTICATION--------------------------------->

    @POST("/auth/register")
    fun registerUser(@Body user: User): Call<Token>

    @POST("/auth/login")
    fun loginUser(@Body user: User): Call<Token>


    //Favorite------------------------------------------>

    @POST("/data/favorite/")
    fun favorite(@Body favorite: Favorite): Call<Favorite>

    @GET("/data/favorite/")
    fun getfavorite(): Call<List<Favorite>>
}