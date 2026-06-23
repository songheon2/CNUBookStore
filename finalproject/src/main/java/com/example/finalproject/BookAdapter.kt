package com.example.finalproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemBookBinding

class BookAdapter(
    private val bookList: List<Book>,
    private val onItemClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(private val binding: ItemBookBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.ivBookCover.setImageResource(book.coverResId)
            binding.tvBookTitle.text = book.title
            binding.tvBookAuthor.text = book.author
            binding.tvBookPrice.text = "${String.format("%,d", book.price)}원"

            // 카드 클릭 리스너
            binding.root.setOnClickListener {
                onItemClick(book)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount(): Int = bookList.size
}
