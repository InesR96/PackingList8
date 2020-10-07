package com.example.packinglist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hide = supportActionBar?.hide()

        Handler().postDelayed({val intent=Intent(this@MainActivity,HomeActivity::class.java )
        startActivity(intent);finish();},3000 )


    }
}