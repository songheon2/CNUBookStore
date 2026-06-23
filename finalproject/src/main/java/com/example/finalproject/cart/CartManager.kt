package com.example.finalproject.cart

import com.example.finalproject.Book

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    // 책 담기: 이미 있으면 수량 증가, 없으면 새로 추가
    fun addBook(book: Book) {
        val existing = cartItems.find { it.book.id == book.id }
        if (existing != null) {
            existing.quantity += 1
        } else {
            cartItems.add(CartItem(book, 1))
        }
    }

    // 장바구니에서 항목 삭제
    fun removeItem(book: Book) {
        cartItems.removeAll { it.book.id == book.id }
    }

    // 수량 변경 (장바구니 화면에서 +/- 할 때 사용 가능)
    fun updateQuantity(book: Book, quantity: Int) {
        val existing = cartItems.find { it.book.id == book.id }
        if (existing != null) {
            if (quantity <= 0) {
                removeItem(book)
            } else {
                existing.quantity = quantity
            }
        }
    }

    // 장바구니 전체 목록
    fun getCartItems(): List<CartItem> = cartItems.toList()

    // 툴바 뱃지에 표시할 총 수량 (수량 합계)
    fun getTotalQuantity(): Int = cartItems.sumOf { it.quantity }

    // 총 합계 금액
    fun getTotalPrice(): Int = cartItems.sumOf { it.book.price * it.quantity }

    // 장바구니 비우기
    fun clear() {
        cartItems.clear()
    }
}