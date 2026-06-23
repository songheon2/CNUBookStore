package com.example.finalproject

import java.io.Serializable

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val price: Int,
    val publisher: String,
    val publishDate: String,
    val coverResId: Int,
    val description: String
) : Serializable