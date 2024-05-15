package com.example.api_2recycler_task

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.api_2recycler_task.API_Comments.CommentsResponse
import com.example.api_2recycler_task.API_Posts.PostsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), HorizontalAdapter.myClickListener {
    lateinit var progressBar1: ProgressBar
    lateinit var progressBar2: ProgressBar
    lateinit var image1: ImageView
    lateinit var image2: ImageView
    lateinit var recyclerHorizontal: RecyclerView
    lateinit var recyclerVertical: RecyclerView
    lateinit var horizontalAdapter: HorizontalAdapter
    lateinit var verticalAdapter: VerticalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar1 = findViewById(R.id.progressBar1)
        progressBar2 = findViewById(R.id.progressBar2)
        image1 = findViewById(R.id.imageView1)
        image2 = findViewById(R.id.imageView2)
        recyclerHorizontal = findViewById(R.id.recyclerHorizontal)
        recyclerVertical = findViewById(R.id.recyclerVertical)


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.INTERNET),
                1
            )
        } else {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
                // Internet connection is available
                Toast.makeText(this@MainActivity, "Network is Connected", Toast.LENGTH_LONG).show()

            } else {
                // Internet connection is not available
                Toast.makeText(this@MainActivity, "Network is Not Connected", Toast.LENGTH_LONG)
                    .show()
            }
        }



        RetrofitClient().getRetrofitClient().getPosts().enqueue(object : Callback<PostsResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (response.body()!!.size == 0) {
                        zeroStateOne()
                        return
                    }
                    horizontalAdapter = HorizontalAdapter(responseBody, this@MainActivity)
                    recyclerHorizontal.layoutManager =
                        LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
                    recyclerHorizontal.adapter = horizontalAdapter
                    showHorizontal()
                    horizontalAdapter.notifyDataSetChanged()
                } else {
                    zeroStateOne()
                    Toast.makeText(
                        this@MainActivity,
                        "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_LONG).show()
            }
        }
        )


        RetrofitClient().getRetrofitClient().getComments()
            .enqueue(object : Callback<CommentsResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<CommentsResponse>,
                    response: Response<CommentsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.size == 0) {
                            zeroStateTwo()
                            return
                        }
                        val responseBody = response.body()
                        verticalAdapter = VerticalAdapter(responseBody!!)
                        recyclerVertical.layoutManager =
                            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                        recyclerVertical.adapter = verticalAdapter
                        showVertical()
                        verticalAdapter.notifyDataSetChanged()
                    } else {
                        zeroStateTwo()
                        Toast.makeText(
                            this@MainActivity,
                            "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CommentsResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_LONG).show()
                }
            }
            )
    }


    override fun onClick(position: Int) {
        progressBar2.isVisible = true
        recyclerVertical.isVisible = false
        val allFunctions = RetrofitClient().getRetrofitClient()
        allFunctions.getCommentById("${position + 1}").enqueue(
            object : Callback<CommentsResponse> {
                override fun onResponse(
                    call: Call<CommentsResponse>,
                    response: Response<CommentsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.size == 0) {
                            zeroStateTwo()
                            return
                        }
                        val responseBody = response.body()
                        verticalAdapter = VerticalAdapter(responseBody!!)
                        recyclerVertical.layoutManager =
                            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                        recyclerVertical.adapter = verticalAdapter
                        showVertical()
                        verticalAdapter.notifyDataSetChanged()
                    } else {
                        zeroStateTwo()
                        Toast.makeText(
                            this@MainActivity,
                            "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CommentsResponse>, t: Throwable) {
                    zeroStateTwo()
                    Toast.makeText(this@MainActivity, "Failed to Load", Toast.LENGTH_SHORT).show()
                }

            }
        )
    }


    fun zeroStateOne() {
        progressBar1.isVisible = false
        recyclerHorizontal.isVisible = false
        image1.isVisible = true
    }

    fun zeroStateTwo() {
        progressBar2.isVisible = false
        recyclerVertical.isVisible = false
        image2.isVisible = true
    }

    fun showHorizontal() {
        progressBar1.isVisible = false
        image1.isVisible = false
        recyclerHorizontal.isVisible = true
    }

    fun showVertical() {
        progressBar2.isVisible = false
        image1.isVisible = false
        recyclerVertical.isVisible = true
    }
}
