package com.zack.news.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zack.news.DetailBeritaActivity
import com.zack.news.R
import com.zack.news.api.ApiClient
import com.zack.news.models.BeritaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class beritaAdapter(
    val dataBerita : ArrayList<BeritaResponse.ListItems>

): RecyclerView.Adapter<beritaAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        //inisialisasi widget
        val imgBerita = view.findViewById<ImageView>(R.id.imgBerita)
        val tvjudul = view.findViewById<TextView>(R.id.tvjudul)
        val tvtgl = view.findViewById<TextView>(R.id.tvtgl)
        val tvrating = view.findViewById<TextView>(R.id.tvrating)
        val ratingbar = view.findViewById<RatingBar>(R.id.ratingbar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_news,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return dataBerita.size

    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        //tampilkan data
        val hasilResponse = dataBerita[position]

        Picasso.get().load(hasilResponse.gambar_berita).into(holder.imgBerita)
        holder.tvjudul.text = hasilResponse.judul
        holder.tvtgl.text = hasilResponse.tgl_berita_indonesia
        holder.tvrating.text = "${hasilResponse.rating}"
        holder.ratingbar.rating = hasilResponse.rating.toFloat()


        holder.itemView.setOnClickListener(){
            val intent = Intent(holder.itemView.context,DetailBeritaActivity::class.java).apply {
                putExtra("gambar",hasilResponse.gambar_berita)
                putExtra("judul" , hasilResponse.judul)
                putExtra("tgl_berita", hasilResponse.tgl_berita_indonesia)
                putExtra("rating",hasilResponse.rating)
                putExtra("isi",hasilResponse.isi_berita)

            }
            holder.imgBerita.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Konfirmasi")
                setMessage("Apakah anda ingin melanjutkan?")
                setIcon(R.drawable.ic_delete)

                setPositiveButton("Yakin") { dialogInterface, i ->
                    ApiClient.apiService.delBerita(hasilResponse.id)
                        .enqueue(object : Callback<BeritaResponse> {
                            override fun onResponse(
                                call: Call<BeritaResponse>,
                                response: Response<BeritaResponse>
                            ) {
                                if (response.body()!!.succes) {
                                    removeItem(position)
                                } else {
                                    Toast.makeText(
                                        holder.itemView.context,
                                        response.body()!!.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }

                            override fun onFailure(call: Call<BeritaResponse>, t: Throwable) {
                                Toast.makeText(
                                    holder.itemView.context,
                                    "Ada Kesalahan Server",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        })
                    dialogInterface.dismiss()
                }

                setNegativeButton("Batal") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
            }.show()

            true
        }


    }

    fun removeItem(position: Int) {
        dataBerita.removeAt(position)
        notifyItemRemoved(position) // Notify the position of the removed item
        notifyItemRangeChanged(position, dataBerita.size - position) // Optional: Adjust for index shifts
    }

    fun setData(data: List<BeritaResponse.ListItems>){
        dataBerita.clear()
        dataBerita.addAll(data)
    }
}