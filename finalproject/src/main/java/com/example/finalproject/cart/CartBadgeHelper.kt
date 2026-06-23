package com.example.finalproject.cart

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.example.finalproject.R

/**
 * 모든 화면의 Toolbar에서 장바구니 아이콘 + 뱃지를 동일하게 처리하기 위한 헬퍼.
 *
 * 사용법 (각 Activity에서):
 *   override fun onCreateOptionsMenu(menu: Menu): Boolean {
 *       menuInflater.inflate(R.menu.menu_main, menu) // 또는 menu_cart
 *       CartBadgeHelper.setup(this, menu)
 *       return true
 *   }
 *
 *   override fun onResume() {
 *       super.onResume()
 *       CartBadgeHelper.refresh(menu) // menu를 멤버 변수로 보관해뒀다가 호출
 *   }
 */

object CartBadgeHelper {
    // 메뉴 inflate 시점에 아이콘 클릭 리스너 + 초기 뱃지 표시 설정
    fun setup(activity: Activity, menu: Menu) {
        val cartItem = menu.findItem(R.id.action_cart) ?: return
        val actionView = cartItem.actionView ?: return

        actionView.setOnClickListener {
            activity.startActivity(Intent(activity, CartActivity::class.java))
        }

        refresh(menu)
    }

    // 장바구니 수량 변경 후 뱃지 숫자만 갱신
    fun refresh(menu: Menu?) {
        val cartItem = menu?.findItem(R.id.action_cart) ?: return
        val actionView = cartItem.actionView ?: return
        val badgeText = actionView.findViewById<TextView>(R.id.tvCartBadge) ?: return

        val count = CartManager.getTotalQuantity()
        if (count > 0) {
            badgeText.visibility = View.VISIBLE
            badgeText.text = if (count > 99) "99+" else count.toString()
        } else {
            badgeText.visibility = View.GONE
        }
    }
}