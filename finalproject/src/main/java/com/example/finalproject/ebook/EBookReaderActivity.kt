package com.example.finalproject.ebook

import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject.databinding.ActivityEbookReaderBinding

class EBookReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEbookReaderBinding
    private var totalPages = 0
    private var currentPage = 0
    private var touchStartX = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEbookReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bookId = intent.getIntExtra("ebook_id", -1)
        val ebook = EBookRepository.getSampleBooks().find { it.bookId == bookId }

        if (ebook == null) {
            finish()
            return
        }

        supportActionBar?.title = ebook.title
        totalPages = ebook.pages.size

        binding.viewPagerEbook.adapter = EBookPagerAdapter(this, ebook.pages)
        binding.viewPagerEbook.setPageTransformer(BookFlipPageTransformer())

        updateIndicator(0)

        binding.viewPagerEbook.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
                updateIndicator(position)
            }
        })

        // 속도와 무관하게, 마지막 페이지에서 손을 뗄 때 이동 거리만으로 판단
        // ViewPager2 내부 구조(getChildAt)에 의존하지 않고,
        // Activity 레벨에서 모든 터치 이벤트를 항상 안정적으로 감지한다.
        // (dispatchTouchEvent는 onCreate 시점이나 뷰 재활용 상태와 무관하게 항상 호출됨)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStartX = event.rawX
            }
            MotionEvent.ACTION_UP -> {
                val deltaX = touchStartX - event.rawX // 양수면 왼쪽으로 이동(다음 페이지 방향)
                val currentItem = binding.viewPagerEbook.currentItem
                val isLastPage = currentItem == totalPages - 1

                if (isLastPage && deltaX > 50) {
                    Toast.makeText(
                        this,
                        "추가 결제를 해야 더 볼 수 있다",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun updateIndicator(position: Int) {
        binding.tvPageIndicator.text = "${position + 1} / $totalPages (무료 미리보기)"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
