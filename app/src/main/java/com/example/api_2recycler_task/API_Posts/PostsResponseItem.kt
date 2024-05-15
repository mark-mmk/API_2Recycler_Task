package com.example.api_2recycler_task.API_Posts

data class PostsResponseItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)