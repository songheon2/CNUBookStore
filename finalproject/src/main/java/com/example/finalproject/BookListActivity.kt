package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.cart.CartBadgeHelper
import com.example.finalproject.databinding.ActivityBookListBinding
import com.google.android.material.navigation.NavigationView

class BookListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityBookListBinding
    private lateinit var bookAdapter: BookAdapter
    private lateinit var toggle: ActionBarDrawerToggle
    private var optionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar 설정
        setSupportActionBar(binding.toolbar)

        // NavigationDrawer 토글
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // NavigationView 리스너
        binding.navigationView.setNavigationItemSelectedListener(this)

        // 뒤로가기
        onBackPressedDispatcher.addCallback(this) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                finish()
            }
        }

        // 더미 데이터 생성
        val bookList = getDummyBooks()

        // RecyclerView 설정
        bookAdapter = BookAdapter(bookList) { book ->
            // 카드 클릭 시 상세 화면으로 이동
            val intent = android.content.Intent(this, BookDetailActivity::class.java)
            intent.putExtra("book", book)
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BookListActivity)
            adapter = bookAdapter
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            R.id.nav_book_list -> {
                Toast.makeText(this, "현재 도서 목록 화면입니다", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_my_library -> {
                Toast.makeText(this, "내 서재 준비 중입니다", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_settings -> {
                Toast.makeText(this, "설정 준비 중입니다", Toast.LENGTH_SHORT).show()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        optionsMenu = menu
        CartBadgeHelper.setup(this, menu)
        return true
    }
    override fun onResume() {
        super.onResume()
        CartBadgeHelper.refresh(optionsMenu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_book_list -> {
                Toast.makeText(this, "현재 도서 목록 화면입니다", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_my_page -> {
                Toast.makeText(this, "마이페이지 준비 중입니다", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // 더미 도서 데이터 (최소 4개)
    private fun getDummyBooks(): List<Book> {
        return listOf(
            Book(
                id = 1,
                title = "코틀린 인 액션",
                author = "드미트리 제메로프, 스베트라나 이사코바",
                price = 36000,
                publisher = "에이콘출판사",
                publishDate = "2017-08-26",
                coverResId = R.drawable.books_book5,
                description = "Kotlin 언어를 깊이 있게 다루는 책. JVM, Android, JavaScript를 대상으로 하는 Kotlin 개발자를 위한 완벽 가이드."
            ),
            Book(
                id = 2,
                title = "안드로이드 프로그래밍 with Kotlin",
                author = "이지스퍼블리싱",
                price = 42000,
                publisher = "이지스퍼블리싱",
                publishDate = "2022-03-15",
                coverResId = R.drawable.books_book6,
                description = "안드로이드 앱 개발의 기초부터 실전까지. Kotlin을 활용한 현대적인 안드로이드 개발 방법을 안내한다."
            ),
            Book(
                id = 3,
                title = "클린 코드",
                author = "로버트 C. 마틴",
                price = 33000,
                publisher = "인사이트",
                publishDate = "2013-12-24",
                coverResId = R.drawable.books_book7,
                description = "읽기 쉽고 유지보수하기 쉬운 코드를 작성하는 방법을 알려주는 소프트웨어 개발 분야의 고전."
            ),
            Book(
                id = 4,
                title = "모던 자바 인 액션",
                author = "라울-게이브리얼 우르마",
                price = 45000,
                publisher = "한빛미디어",
                publishDate = "2019-08-01",
                coverResId = R.drawable.books_book8,
                description = "Java 8, 9, 10 이상의 새로운 기능을 람다, 스트림, 함수형 프로그래밍 관점에서 설명한다."
            ),
        )
    }

}