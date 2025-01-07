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
import com.zack.news.models.regisResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {

    private lateinit var etusername : EditText
    private lateinit var etpassword : EditText
    private lateinit var etfullname : EditText
    private lateinit var etemail : EditText
    private lateinit var btnsignup : Button
    private lateinit var progresbar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        etusername = findViewById(R.id.etUsername)
        etpassword = findViewById(R.id.etPassword)
        etfullname = findViewById(R.id.etFullname)
        etemail = findViewById(R.id.etEmail)
        btnsignup = findViewById(R.id.btnSignup)
        progresbar = findViewById(R.id.progresbar)

        btnsignup.setOnClickListener(){
            prosesRegister()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //method proses register

    fun prosesRegister(){
        progresbar.visibility = View.VISIBLE
        ApiClient.apiService.register(
            etusername.text.toString(),
            etpassword.text.toString(),
            etfullname.text.toString(),
            etemail.text.toString()
        ).enqueue(object: Callback<regisResponse> {
            override fun onResponse(call: Call<regisResponse>,
                                    response: Response<regisResponse>) {


                //Response Berhasil
                if (response.isSuccessful){
                    //Jika Berhasil Menambahkan Data User
                    //Kondisi True => success

                    if (response.body()!!.succes){
                        //Arahkan ke LoginActivity
                        startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
                        Toast.makeText(
                            this@RegisterActivity,
                            response.body()!!.message,
                            Toast.LENGTH_LONG
                        ).show()

                        //Pesan

                    }//Kondisi False => Failed
                    else{

                        //Pesan
                        Toast.makeText(
                            this@RegisterActivity,
                            response.body()!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                progresbar.visibility = View.GONE
            }
            override fun onFailure(call: Call<regisResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,t.message,Toast.LENGTH_LONG).show()
               progresbar.visibility = View.GONE
            }
        })
    }
}