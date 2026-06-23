package com.example.finalproject.cart

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cartAdapter = CartAdapter(emptyList()) { cartItem ->
            CartManager.removeItem(cartItem.book)
            Toast.makeText(this, "${cartItem.book.title} 삭제됨", Toast.LENGTH_SHORT).show()
            refreshCart()
        }

        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }

        binding.btnCheckout.setOnClickListener {
            if (CartManager.getCartItems().isEmpty()) {
                Toast.makeText(this, "장바구니가 비어 있습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "주문 기능은 준비 중입니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshCart()
    }

    private fun refreshCart() {
        val items = CartManager.getCartItems()
        cartAdapter.updateItems(items)

        if (items.isEmpty()) {
            binding.tvEmptyCart.visibility = View.VISIBLE
            binding.recyclerViewCart.visibility = View.GONE
            binding.layoutCartBottom.visibility = View.GONE
        } else {
            binding.tvEmptyCart.visibility = View.GONE
            binding.recyclerViewCart.visibility = View.VISIBLE
            binding.layoutCartBottom.visibility = View.VISIBLE
            binding.tvCartTotalPrice.text = "${String.format("%,d", CartManager.getTotalPrice())}원"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}