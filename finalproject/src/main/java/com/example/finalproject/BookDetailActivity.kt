package com.example.finalproject

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.cart.CartBadgeHelper
import com.example.finalproject.cart.CartManager
import com.example.finalproject.databinding.ActivityBookDetailBinding

class BookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailBinding
    private var currentBook: Book? = null
    private var optionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Toolbar 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail_title)

        // Intent로 Book 객체 수신
        val book = intent.getSerializableExtra("book") as? Book
        currentBook = book

        book?.let {
            bindBookData(it)
        } ?: finish() // book이 null이면 종료

        // 장바구니 담기 버튼
        binding.btnAddToCart.setOnClickListener {
            currentBook?.let {
                CartManager.addBook(it)
                Toast.makeText(this, "${it.title} 장바구니에 담았습니다", Toast.LENGTH_SHORT).show()
                CartBadgeHelper.refresh(optionsMenu)
            }
        }

    }
    private fun bindBookData(book: Book) {
        binding.apply {
            ivDetailCover.setImageResource(book.coverResId)
            tvDetailTitle.text = book.title
            tvDetailAuthor.text = "저자: ${book.author}"
            tvDetailPublisher.text = "출판사: ${book.publisher}"
            tvDetailPublishDate.text = "출판일: ${book.publishDate}"
            tvDetailPrice.text = "${String.format("%,d", book.price)}원"
            tvDetailDescription.text = book.description
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        optionsMenu = menu
        CartBadgeHelper.setup(this, menu)
        return true
    }
    override fun onResume() {
        super.onResume()
        CartBadgeHelper.refresh(optionsMenu)
    }

    // 툴바 뒤로가기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}