package com.example.finalproject.ebook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemEbookBinding

class EBookAdapter(
    private val ebookList: List<EBookSample>,
    private val onItemClick: (EBookSample) -> Unit
) : RecyclerView.Adapter<EBookAdapter.EBookViewHolder>() {

    inner class EBookViewHolder(private val binding: ItemEbookBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(ebook: EBookSample) {
            binding.ivEbookCover.setImageResource(ebook.coverResId)
            binding.tvEbookTitle.text = ebook.title
            binding.tvEbookAuthor.text = ebook.author
            binding.tvEbookFreePages.text = "무료 ${ebook.pages.size}페이지 미리보기"

            binding.root.setOnClickListener {
                onItemClick(ebook)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EBookViewHolder {
        val binding = ItemEbookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EBookViewHolder, position: Int) {
        holder.bind(ebookList[position])
    }

    override fun getItemCount(): Int = ebookList.size
}