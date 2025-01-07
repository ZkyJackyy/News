package com.zack.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast


import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zack.news.api.ApiClient
import com.zack.news.models.loginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etusername : EditText
    private lateinit var etpassword : EditText
    private lateinit var btnsignin : Button
    private  lateinit var progresbar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        etusername = findViewById(R.id.etUsername)
        etpassword = findViewById(R.id.etPassword)
        btnsignin = findViewById(R.id.btnsignin)
        progresbar = findViewById(R.id.progresbar)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnsignin.setOnClickListener(){
            proseslogin()
        }
    }

    private fun proseslogin(){
        progresbar.visibility = View.VISIBLE
        ApiClient.apiService.login(
            etusername.text.toString(),
            etpassword.text.toString()
        ).enqueue(object : Callback<loginResponse>{
            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {

                if(response.isSuccessful){
                    if (response.body()!!.succes){
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        Toast.makeText(
                            this@LoginActivity,
                            response.body()!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }else{
                        Toast.makeText(
                            this@LoginActivity,
                            response.body()!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                }

                }else{
                    Toast.makeText(
                        this@LoginActivity,
                        "Login gagal: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                progresbar.visibility = View.GONE
            }

            override fun onFailure(call: Call<loginResponse>, t: Throwable) {

                Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_LONG).show()


                progresbar.visibility = View.GONE
            }
        })
    }
}