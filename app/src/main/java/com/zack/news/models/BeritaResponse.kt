package com.zack.news.models

data class BeritaResponse(
    val succes : Boolean,
    val message : String,
    val data: ArrayList<ListItems>
){
    data class ListItems(
        val id: String,
        val judul : String,
        val isi_berita : String,
        val gambar_berita : String,
        val tgl_berita_indonesia : String,
        val rating : Double
    )
}
