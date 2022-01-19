package com.example.kotlinflow

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinflow.adapter.PostAdapter
import com.example.kotlinflow.databinding.ActivityMainBinding
import com.example.kotlinflow.model.Post
import com.example.kotlinflow.viewmodel.PostViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter
    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        postViewModel.getPost()
        postViewModel.postData.observe(this, Observer {
            postAdapter.setPostData(it as ArrayList<Post>)
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        })

        val data = flowOf(1, 2, 3, 4, 5, 6).flowOn(Dispatchers.IO)

        val data1 = listOf("One", "Two", "Three", "Four", "Five").asFlow().flowOn(Dispatchers.IO)

        val data2 = flowOf(1, 2, 3, 4, 5).flowOn(Dispatchers.IO)

        val data3 = flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).flowOn(Dispatchers.IO)


        runBlocking<Unit> {
            // Launch a concurrent coroutine to check if the main thread is blocked
            launch {
                for (k in 1..3) {
                    println("I'm not blocked $k")
                    delay(100)
                }

                println("------------------ flowOf ------------------")
                data.collect {
                    Log.d(TAG, "onCreate: $it")
                }

                println("------------------ asFlow ------------------")
                data1.collect {
                    Log.d(TAG, "onCreate: $it")
                }

                println("------------------ map ------------------")
                data2.map { value ->
                    value * value
                }.collect {
                    Log.d(TAG, "onCreate: $it")
                }

                println("------------------ filter ------------------")
                data3.filter { value ->
                    value % 2 == 0
                }.collect {
                    Log.d(TAG, "onCreate: $it")
                }
            }

        }

        GlobalScope.launch {
            simple().collect { value -> println(value) }
        }
    }

    private fun simple(): Flow<Int> = flow { // flow builder
        for (i in 1..3) {
            delay(100) // pretend we are doing something useful here
            emit(i) // emit next value
        }
    }

    private fun init() {
        postAdapter = PostAdapter(this, ArrayList())
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }
    }

}