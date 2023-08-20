package com.virua.simpleviralgamesassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.collection.LruCache
import java.util.*
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
var BASE_URL = ""
class DogsActivity : AppCompatActivity() {

    companion object {
        var imageUrl=""
    }
    private lateinit var generateButton: Button
    private lateinit var dogImageView: ImageView
    private val cache = LruCache<String, String>(20)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs)


        generateButton = findViewById(R.id.generateButton)
        dogImageView = findViewById(R.id.dogImageView)
//    var retrofit = Retrofit.Builder().baseUrl(imageUrl).addConverterFactory(GsonConverterFactory.create()).build()
        generateButton.setOnClickListener {
            getMyData();
//            Glide.with(this).load(imageUrl).into(dogImageView)
//            Log.d("DogsActivity", "Compleated hghfhgchg" +imageUrl )
//            Toast.makeText(applicationContext, ""+ imageUrl, Toast.LENGTH_LONG).show()
        }
    }
    private fun getMyData() {
        var retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dog.ceo/api/breeds/image/").build().create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<MyData> {
            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                val responseBody = response.body()!!
                imageUrl=responseBody.message
                BASE_URL= imageUrl
                try {
                    Glide.with(this@DogsActivity).load(imageUrl).into(dogImageView)
                } catch (e:Exception) {}

                ImageManager.getIntance().addImageLink(imageUrl)
//                Toast.makeText(applicationContext, ""+ imageUrl, Toast.LENGTH_LONG).show()
                Log.d("DogsActivity", "Compleated "  + BASE_URL)
            }
            override fun onFailure(call: Call<MyData>, t: Throwable) {
                Log.d("DogsActivity", "onFailure: "+ t.message )
            }
        })
    }

}