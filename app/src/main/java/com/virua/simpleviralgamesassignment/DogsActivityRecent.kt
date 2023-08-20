package com.virua.simpleviralgamesassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LruCache
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DogsActivityRecent : AppCompatActivity() {
    private val cacheSize = 20 // Maximum number of images to store in the cache
    private val cache = LruCache<String, String>(cacheSize)
    private lateinit var recyclerView: RecyclerView
    private lateinit var clearButton: Button
    private val images by lazy{
        ImageManager.getIntance().getImageLinks()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs_recent)

        recyclerView = findViewById(R.id.recyclerView)
        clearButton = findViewById(R.id.clearButton)

        // Initialize RecyclerView with an adapter
        val adapter = DogsAdapter(images) // Implement this function to retrieve stored image URLs
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter = adapter

        clearButton.setOnClickListener {
            // Clear cache and update RecyclerView
//            adapter.notifyDataSetChanged()
            ImageManager.getIntance().clearImageLinks()
            adapter.setItems(emptyList())
        }
    }


}