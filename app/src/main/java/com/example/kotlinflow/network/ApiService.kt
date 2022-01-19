package com.example.kotlinflow.network

import com.example.kotlinflow.model.Post
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPost() : List<Post>

}