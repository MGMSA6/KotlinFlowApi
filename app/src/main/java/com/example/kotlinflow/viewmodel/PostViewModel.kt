package com.example.kotlinflow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflow.Repository.PostRepository
import com.example.kotlinflow.model.Post
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val TAG = "PostViewModel"

    val postData: MutableLiveData<List<Post>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            PostRepository.getPost()
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }
                .collect { postData1 ->
                    postData.value = postData1
                }
        }
    }
}