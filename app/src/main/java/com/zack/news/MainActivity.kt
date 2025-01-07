package com.zack.news

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zack.news.api.ApiClient
import com.zack.news.models.regisRequest
import com.zack.news.models.regisResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var btnRegis : Button
    private lateinit var btnlogin : Button
    private val storagePermission = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        btnRegis = findViewById(R.id.btnRegis)
        btnlogin = findViewById(R.id.btnLogin)


        btnRegis.setOnClickListener(){
            startActivity(Intent(this,RegisterActivity::class.java))
        }


        btnlogin.setOnClickListener(){
            startActivity(Intent(this,LoginActivity::class.java))
        }

        checkStoragePermission()
    }

    private fun checkStoragePermission(){
        if(ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
        )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                storagePermission
            )
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }
    }
}