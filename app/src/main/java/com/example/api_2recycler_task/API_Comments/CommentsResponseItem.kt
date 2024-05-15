package com.example.api_2recycler_task.API_Comments

data class CommentsResponseItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)