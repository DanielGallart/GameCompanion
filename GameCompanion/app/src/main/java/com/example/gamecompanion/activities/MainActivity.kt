package com.example.gamecompanion.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamecompanion.fragment.ProfileFragment
import com.example.gamecompanion.R
import com.example.gamecompanion.fragment.StreamsFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Load Ad
        MobileAds.initialize(this){}
        val adRequest = AdRequest.Builder().build()
        bannerAdView.loadAd(adRequest)


        bottomNavigationView.setOnNavigationItemSelectedListener{ menuItem ->
            when(menuItem.itemId){
                R.id.chat ->{
                    /*val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
                    FirebaseAnalytics.getInstance(this).logEvent("Chat_Tab_Click", null)*/
                }
                R.id.feed ->{

                }
                R.id.profile ->{
                    //Create Fragment
                    val profileFragment = ProfileFragment()
                    //Add Fragment to Fragment Container
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction= fragmentManager.beginTransaction()
                    fragmentTransaction.add(fragmentContainer.id, profileFragment)
                    fragmentTransaction.commit()
                }
                R.id.streams ->{
                    FirebaseAnalytics.getInstance(this).logEvent("Streams_Tab_Click",null)
                    //Create Fragment
                    val fragment = StreamsFragment()
                    //Add Fragment to Fragment Container
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction= fragmentManager.beginTransaction()
                    fragmentTransaction.add(fragmentContainer.id, fragment)
                    fragmentTransaction.commit()
                }


            }
           true
        }
    }
}
