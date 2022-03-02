package com.example.reto1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var loginBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn = findViewById(R.id.loginBtn)

        loginBtn.setOnClickListener{
            val intent = Intent(this,NavigationActivity::class.java)
            startActivity(intent)
        }
    }
}