package com.example.gamecompanion.network

import com.example.gamecompanion.model.StreamModel
import com.example.gamecompanion.model.StreamsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers


interface TwitchApiService {

    @Headers("Client-ID: ywvglt0gib8rqdly0ejobehqfi071m")
    @GET("/streams")
    fun getStreams(): retrofit2.Call<StreamsResponse>

    //Create https client
    companion object{
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix")
            .build()
        //Endpoints
        val endPoints = retrofit.create<TwitchApiService>(TwitchApiService::class.java)
    }

}