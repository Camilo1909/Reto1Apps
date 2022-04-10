package com.example.reto1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.reto1.databinding.ActivityMainBinding
import com.example.reto1.databinding.ActivityNavigationBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var loginBtn: Button
    private lateinit var binding: ActivityMainBinding

    private var user: User? = null
    private var nuser: User? = null
    var allGrant = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loginBtn = findViewById(R.id.loginBtn)

        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 1
        )
        loginBtn.setOnClickListener {
            if (allGrant) {
                if (validation(
                        binding.usernameEt.text.toString(),
                        binding.passwordET.text.toString()
                    )
                ) {
                    val intent = Intent(this, NavigationActivity::class.java)
                    intent.putExtra("logUser", Gson().toJson(user))
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Por favor ingrese credenciales correctas",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this, "Por favor aceptar todos los permisos", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun validation(username: String, password: String): Boolean {
        val user1 = User("Juan", "alfa@gmail.com")
        val user2 = User("Camilo", "beta@gmail.com")
        var validate = false
        if ((username.contentEquals(user1.userName) && password.contentEquals(user1.password))
            || (username.contentEquals(user2.userName) && password.contentEquals(user2.password))
        ) {
            validate = true
            user = User("Name", username)
        }
        return validate
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            for (result in grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) allGrant = false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val json = Gson().toJson(user)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit().putString("currentState", json).apply()
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val json = sharedPref.getString("currentState", "NO_DATA")
        val logUser = intent.getStringExtra("logUser")
        nuser = Gson().fromJson(logUser,User::class.java)
        Log.e("---->","${nuser}")
        if (json != "NO_DATA") {
            user = Gson().fromJson(json, User::class.java)
            Log.e("---->","${user}")
            Log.e("---->","${nuser}")
            if(user?.userName!=nuser?.userName){
                val intent = Intent(this, NavigationActivity::class.java)
                intent.putExtra("logUser", Gson().toJson(user))
                startActivity(intent)
            }else{
                sharedPref.edit().remove("currentState").commit()
              //  sharedPref.edit().clear().apply()
            }
        }
    }

    fun getUser():User?{
        return user
    }
}

