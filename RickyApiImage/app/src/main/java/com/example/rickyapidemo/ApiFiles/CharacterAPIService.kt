package com.example.myapplication.ApiFiles

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterAPIService {
    // GET ALL CHARACTERS BY PAGE
    @GET("character")
    fun getAll(@Query("page") page: Int): Call<CharacterResponse>
}
