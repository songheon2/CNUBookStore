package com.example.finalproject.ebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.finalproject.databinding.FragmentEbookPageBinding

class EBookPageFragment: Fragment() {

    private var _binding: FragmentEbookPageBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_PAGE_CONTENT = "page_content"

        fun newInstance(pageContent: String): EBookPageFragment {
            val fragment = EBookPageFragment()
            val args = Bundle()
            args.putString(ARG_PAGE_CONTENT, pageContent)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEbookPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val content = arguments?.getString(ARG_PAGE_CONTENT) ?: ""
        binding.tvPageContent.text = content
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}