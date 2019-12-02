package com.example.gamecompanion.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gamecompanion.R
import com.example.gamecompanion.model.StreamsResponse
import com.example.gamecompanion.network.TwitchApiService
import retrofit2.Callback
import okhttp3.Response
import java.io.IOException


class StreamsFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_streams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TwitchApiService.endPoints.getStreams().enqueue(object : retrofit2.Callback<StreamsResponse>{
            override fun onFailure(call: retrofit2.Call<StreamsResponse>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: retrofit2.Call<StreamsResponse>,
                response: retrofit2.Response<StreamsResponse>
            ) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Log.i("StreamsFragment", "++ onResponse ++")
                if(response.isSuccessful){ // code==200
                    //All good
                    Log.i("StreamsFragment", response.body()?.toString() ?: "Null body")
                }
                else{
                    Log.w("StreamsFragment",response.message())
                }
            }


        })
    }
}