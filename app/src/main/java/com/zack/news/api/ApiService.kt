package com.zack.news.api

import com.zack.news.models.BeritaResponse
import com.zack.news.models.loginRequest
import com.zack.news.models.loginResponse
import com.zack.news.models.regisRequest
import com.zack.news.models.regisResponse
import com.zack.news.models.tambahResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("API_BASIC/register.php")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullname: String,
        @Field("email") email: String,

    ):Call<regisResponse>

    @FormUrlEncoded
    @POST("/API_BASIC/login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<loginResponse>

    @FormUrlEncoded
    @POST("API_BASIC/delete_berita.php")
    fun delBerita(
        @Field("id") id: String
    ): Call<BeritaResponse>


    @GET("API_BASIC/get_berita.php")
    fun getListBerita(
        @Query("judul") judul : String
    ): Call<BeritaResponse>

    @Multipart
    @POST("API_BASIC/add_berita.php")
    fun addBerita(
        @Part("judul") judul: RequestBody,
        @Part("isi_berita") isi_berita :RequestBody,
        @Part fileGambar: MultipartBody.Part
    ): Call<tambahResponse>
}