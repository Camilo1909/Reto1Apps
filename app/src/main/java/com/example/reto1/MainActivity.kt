package com.example.reto1

import android.Manifest
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

class MainActivity : AppCompatActivity() {

    private lateinit var loginBtn:Button

    private lateinit var binding: ActivityMainBinding

    var allGrant = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loginBtn = findViewById(R.id.loginBtn)

        requestPermissions(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ),1)
        loginBtn.setOnClickListener{
            if (allGrant){
                if(validation(binding.usernameEt.text.toString(),binding.passwordET.text.toString())){
                    val intent = Intent(this,NavigationActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Por favor ingrese credenciales correctas",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"Por favor aceptar todos los permisos",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun validation(username:String, password:String):Boolean{
        var validate = false
        if((username.contentEquals("alfa@gmail.com")&&password.contentEquals("aplicacionesmoviles"))
            || (username.contentEquals("beta@gmail.com")&&password.contentEquals("aplicacionesmoviles"))){
            validate = true
        }
        Log.e(">>>","${validate}")
        return validate
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1){
            for(result in grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) allGrant = false
            }
        }
    }
}