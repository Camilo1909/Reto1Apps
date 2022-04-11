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
    private var allGrant = true

    private var user1 = User("Juan", "alfa@gmail.com")
    private var user2 = User("Camilo", "beta@gmail.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loginBtn = findViewById(R.id.loginBtn)

        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
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

        var validate = false
        if ((username.contentEquals(user1.userName) && password.contentEquals(user1.password))
            || (username.contentEquals(user2.userName) && password.contentEquals(user2.password))
        ) {
            if (username.contentEquals(user1.userName)){
                validate = true
                user = user1
            }else{
                validate = true
                user = user2
            }
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
        val json1 = Gson().toJson(user1)
        val json2 = Gson().toJson(user2)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString("currentState", json)
           // .putString("currentState1", json1)
            //.putString("currentState2", json2)
            .apply()
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
                if (nuser?.userName==user1.userName){
                    user1.name = nuser?.name!!
                    user1.imgProfile = nuser?.imgProfile!!
                    val json1u = Gson().toJson(user1)
                    sharedPref.edit()
                        .putString("currentState1", json1u)
                        .apply()
                }else if(nuser?.userName==user2.userName){
                    user2.name = nuser?.name!!
                    user2.imgProfile = nuser?.imgProfile!!
                    val json2u = Gson().toJson(user2)
                    sharedPref.edit()
                        .putString("currentState2", json2u)
                        .apply()
                }
              //  sharedPref.edit().clear().apply()
            }
        }
        val json1 = sharedPref.getString("currentState1", "NO_DATA")
        val json2 = sharedPref.getString("currentState2", "NO_DATA")
        if (json1 != "NO_DATA"){
            user1 = Gson().fromJson(json1, User::class.java)
        }
        if (json2 != "NO_DATA"){
            user2 = Gson().fromJson(json2, User::class.java)
        }


    }

    fun getUser():User?{
        return user
    }
}

