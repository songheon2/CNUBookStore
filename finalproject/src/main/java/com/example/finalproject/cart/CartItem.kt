package com.example.finalproject.cart

import com.example.finalproject.Book

data class CartItem(
    val book: Book,
    var quantity: Int
)
