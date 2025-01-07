package com.zack.news

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class DetailBeritaActivity : AppCompatActivity() {

    private lateinit var imgberita : ImageView
    private lateinit var tvjudul : TextView
    private lateinit var tgl_detail_berita : TextView
    private lateinit var rating : TextView
    private lateinit var  ratingBar: RatingBar
    private lateinit var tvisi : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_berita)

        imgberita = findViewById(R.id.imgdetail)
        tvjudul = findViewById(R.id.tvjudul)
        tgl_detail_berita = findViewById(R.id.tvtglberita)
        rating = findViewById(R.id.tvrating)
        ratingBar = findViewById(R.id.ratingbar)
        tvisi = findViewById(R.id.tvisiBerita)

        Picasso.get().load(intent.getStringExtra("gambar")).into(imgberita)
        tvjudul.text = intent.getStringExtra("judul")
        tgl_detail_berita.text = intent.getStringExtra("tgl_berita")
        rating.text = "${intent.getDoubleExtra("rating",0.0)}"
        ratingBar.rating = intent.getDoubleExtra("rating",0.0).toFloat()
        tvisi.text = intent.getStringExtra("isi")


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}