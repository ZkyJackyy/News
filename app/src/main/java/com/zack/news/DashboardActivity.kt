package com.zack.news

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zack.news.adapter.beritaAdapter
import com.zack.news.api.ApiClient
import com.zack.news.models.BeritaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var svjudul :SearchView
    private lateinit var progresbar : ProgressBar
    private lateinit var rvberita :RecyclerView
    private lateinit var btntambah : FloatingActionButton
    private lateinit var beritaadapter : beritaAdapter
    private lateinit var imgnotFound : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        svjudul = findViewById(R.id.svjudul)
        progresbar = findViewById(R.id.progresbar)
        rvberita = findViewById(R.id.rvberita)
        btntambah= findViewById(R.id.floatBtnTambah)
        imgnotFound = findViewById(R.id.imgnotFound)


        btntambah.setOnClickListener(){
            startActivity(Intent(this@DashboardActivity,TambahBeritaActivity::class.java))
        }



        //panggil method get berita

        getBerita("")


        svjudul.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(pencarian: String?): Boolean {
                getBerita(pencarian.toString())
                return true
            }
        })

    }
    @SuppressLint("SuspiciousIndentation")
    private fun getBerita(judul: String){
        progresbar.visibility = View.VISIBLE
        ApiClient.apiService.getListBerita(judul).enqueue(object: Callback<BeritaResponse> {
            override fun onResponse(
                call: Call<BeritaResponse>,
                response: Response<BeritaResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.succes){
                        //set data
                        beritaadapter = beritaAdapter(arrayListOf())
                        rvberita.adapter = beritaadapter
                        beritaadapter.setData(response.body()!!.data)
                        imgnotFound.visibility = View.GONE
                    }else{
                        //jika data tidak ditemukan
                        beritaadapter = beritaAdapter(arrayListOf())
                        rvberita.adapter = beritaadapter
                        imgnotFound.visibility = View.VISIBLE
                    }
                }
                progresbar.visibility =View.GONE
            }

            override fun onFailure(call: Call<BeritaResponse>, t: Throwable) {
                Toast.makeText(this@DashboardActivity,"error : ${t.message}",Toast.LENGTH_LONG)
                    .show()
                progresbar.visibility =View.GONE
            }
        })
    }
}