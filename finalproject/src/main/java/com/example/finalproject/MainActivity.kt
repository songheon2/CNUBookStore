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
import com.example.finalproject.cart.CartBadgeHelper
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.ebook.EBookListActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private var optionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar 설정
        setSupportActionBar(binding.toolbar)

        // NavigationDrawer 토글
        toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.nav_open, R.string.nav_close
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
        // 메뉴 버튼 클릭
        binding.btnBookList.setOnClickListener {
            startActivity(Intent(this, BookListActivity::class.java))
        }
        binding.btnEBook.setOnClickListener {
            startActivity(Intent(this, EBookListActivity::class.java))
        }
        binding.btnCustomerService.setOnClickListener {
            Toast.makeText(this, "고객문의 준비 중입니다", Toast.LENGTH_SHORT).show()
        }
        binding.btnMyPage.setOnClickListener {
            Toast.makeText(this, "나의페이지 준비 중입니다", Toast.LENGTH_SHORT).show()
        }
        binding.btnMarketInfo.setOnClickListener {
            Toast.makeText(this, "마켓정보 준비 중입니다", Toast.LENGTH_SHORT).show()
        }
        binding.btnSettings.setOnClickListener {
            Toast.makeText(this, "설정 준비 중입니다", Toast.LENGTH_SHORT).show()
        }
    }
    // NavigationDrawer 메뉴 아이템 클릭 처리
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                Toast.makeText(this, "홈 화면입니다", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_book_list -> {
                startActivity(Intent(this, BookListActivity::class.java))
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

    // Toolbar 옵션 메뉴 inflate
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

    // Toolbar 옵션 메뉴 클릭
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_book_list -> {
                startActivity(Intent(this, BookListActivity::class.java))
                true
            }
            R.id.action_my_page -> {
                Toast.makeText(this, "마이페이지 준비 중입니다", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}