package com.example.finalproject.ebook

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.cart.CartBadgeHelper
import com.example.finalproject.databinding.ActivityEbookListBinding

class EBookListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEbookListBinding
    private var optionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEbookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.ebook_title)

        val ebookList = EBookRepository.getSampleBooks()

        val adapter = EBookAdapter(ebookList) { ebook ->
            val intent = Intent(this, EBookReaderActivity::class.java)
            intent.putExtra("ebook_id", ebook.bookId)
            startActivity(intent)
        }

        binding.recyclerViewEbook.apply {
            layoutManager = LinearLayoutManager(this@EBookListActivity)
            this.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        CartBadgeHelper.refresh(optionsMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        optionsMenu = menu
        CartBadgeHelper.setup(this, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}