package com.virua.simpleviralgamesassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImageManager.init(applicationContext)
        findViewById<View>(R.id.generateButton).setOnClickListener {
            startActivity(Intent(this, DogsActivity::class.java))
        }

        findViewById<View>(R.id.recentDogsButton).setOnClickListener {
            startActivity(Intent(this, DogsActivityRecent::class.java))
        }
    }

}