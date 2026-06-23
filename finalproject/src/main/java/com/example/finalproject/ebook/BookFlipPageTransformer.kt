package com.example.finalproject.ebook

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * ViewPager2용 책장 넘김 PageTransformer (v4).
 *
 * v3에서 발생한 문제: 그림자 표현을 위해 코드로 동적 View를 CardView 자식으로 추가했는데,
 * CardView의 elevation 그림자와 겹쳐 우측에 사선 모양 렌더링 артефакт가 고정적으로 나타남.
 *
 * v4 해결: 별도 View를 추가하지 않고, ColorMatrix 대신 단순히 page의 alpha만 살짝 낮춰서
 * "어두워지는" 느낌을 표현. 더 단순하지만 부작용 없음.
 *
 * - 이전 페이지(position < 0): 완전히 고정, 변형 없음
 * - 다음 페이지(position > 0): 왼쪽 끝을 축으로 회전 + 스케일 축소 + alpha로 살짝 어두운 느낌
 */
class BookFlipPageTransformer : ViewPager2.PageTransformer {

    companion object {
        private const val MIN_SCALE = 0.92f
        private const val MAX_ROTATION = 95f
        private const val MIN_ALPHA = 0.55f // 다음 페이지가 가장 멀리 있을 때(접힌 상태)의 최소 밝기
    }

    override fun transformPage(page: View, position: Float) {
        page.cameraDistance = 25000f

        when {
            // 화면 밖으로 완전히 사라진 페이지
            position < -1f || position > 1f -> {
                page.alpha = 0f
            }

            // 이전 페이지: 변형 없이 고정 (화면을 빠져나가는 동안에도 그대로 유지)
            position < 0f -> {
                page.alpha = 1f
                page.rotationY = 0f
                page.scaleX = 1f
                page.scaleY = 1f
                page.translationZ = 0f
            }

            // 정확히 중앙
            position == 0f -> {
                page.alpha = 1f
                page.rotationY = 0f
                page.scaleX = 1f
                page.scaleY = 1f
                page.translationZ = 0f
            }

            // 다음 페이지(앞으로 갈 때 들어오는 페이지 / 뒤로 갈 때 빠져나가는 페이지):
            // 회전 + 축소 + alpha. absPos가 1에 가까워질수록(거의 접힌 상태) 완전히 투명해지도록 하여
            // 뒤로 가기 시 고정된 페이지 옆으로 모서리가 비치는 현상을 방지한다.
            else -> {
                val absPos = abs(position) // 1(화면 밖, 접힌 상태) -> 0(중앙, 평평하게 펼쳐짐)

                page.pivotX = 0f
                page.pivotY = page.height * 0.5f
                page.rotationY = MAX_ROTATION * absPos

                val scale = MIN_SCALE + (1f - MIN_SCALE) * (1f - absPos)
                page.scaleX = scale
                page.scaleY = scale

                page.translationZ = 10f

                // absPos > 0.85 구간(거의 다 접힌 상태)에서는 완전히 0으로 떨어뜨려
                // 회전판이 얇아 보이는 가장자리가 옆 페이지에 비치지 않도록 한다.
                page.alpha = if (absPos > 0.85f) {
                    val fadeProgress = (1f - absPos) / 0.15f // 0.85~1.0 구간을 0~1로 재매핑
                    MIN_ALPHA * fadeProgress
                } else {
                    MIN_ALPHA + (1f - MIN_ALPHA) * (1f - absPos)
                }
            }
        }
    }
}