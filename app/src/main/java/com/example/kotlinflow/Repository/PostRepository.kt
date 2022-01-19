package com.example.kotlinflow.Repository

import com.example.kotlinflow.model.Post
import com.example.kotlinflow.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostRepository {

    companion object {
        fun getPost(): Flow<List<Post>> = flow {
            val postList = RetrofitBuilder.api.getPost()
            emit(postList)
        }.flowOn(Dispatchers.IO)
    }
}