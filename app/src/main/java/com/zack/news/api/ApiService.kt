package com.zack.news.api

import com.zack.news.models.loginRequest
import com.zack.news.models.loginResponse
import com.zack.news.models.regisRequest
import com.zack.news.models.regisResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("API_BASIC/register.php")
    fun register(@Body request: regisRequest): Call<regisResponse>

    @POST("API_BASIC/login.php")
    fun login(@Body request: loginRequest): Call<loginResponse>
}