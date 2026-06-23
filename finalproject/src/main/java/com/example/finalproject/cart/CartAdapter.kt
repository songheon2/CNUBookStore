package com.example.finalproject.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemCartBinding

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onRemoveClick: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            val book = item.book
            binding.ivCartCover.setImageResource(book.coverResId)
            binding.tvCartTitle.text = book.title
            binding.tvCartUnitPrice.text = "${String.format("%,d", book.price)}원"
            binding.tvCartQuantity.text = "수량: ${item.quantity}개"
            binding.tvCartSubtotal.text = "${String.format("%,d", book.price * item.quantity)}원"

            binding.ivCartRemove.setOnClickListener {
                onRemoveClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateItems(newItems: List<CartItem>) {
        cartItems = newItems
        notifyDataSetChanged()
    }
}