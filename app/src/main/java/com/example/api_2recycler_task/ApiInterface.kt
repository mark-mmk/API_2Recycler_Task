package com.example.api_2recycler_task

import com.example.api_2recycler_task.API_Comments.CommentsResponse
import com.example.api_2recycler_task.API_Posts.PostsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("posts")
    fun getPosts(): Call<PostsResponse>
    @GET("comments")
    fun getComments():Call<CommentsResponse>
    @GET("comments")
    fun getCommentById(@Query("postId") postId: String):Call<CommentsResponse>
}