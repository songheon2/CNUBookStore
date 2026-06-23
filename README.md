# 📚 CNUBookStore

안드로이드 도서 쇼핑 앱 — 도서 목록 조회, 상세 정보 확인, 장바구니, E-Book 무료 미리보기 기능을 제공하는 Kotlin 기반 안드로이드 애플리케이션입니다.

## 앱 소개

CNUBookStore는 사용자가 책을 둘러보고, 장바구니에 담고, 구매 전 일부 내용을 미리 읽어볼 수 있는 통합 도서 앱입니다.

## 필수 화면 구성

| # | 화면 | 설명 |
|---|------|------|
| 1 | 스플래시 화면 | 앱 실행 시 2.5초 표시 후 메인 화면으로 자동 전환 |
| 2 | 메인(홈) 화면 | 앱 로고·환영 문구, Toolbar + NavigationDrawer |
| 3 | 도서 목록 화면 | RecyclerView + CardView, 최소 5개 도서 더미 데이터 |
| 4 | 도서 상세 화면 | 표지·제목·저자·가격·출판일 표시, Intent로 데이터 전달 |
| 5 | 옵션 메뉴 | Toolbar 메뉴 아이콘 클릭 시 화면 이동 또는 Toast 처리 |

## 구현 기능

### 필수 기능
- RecyclerView + CardView 기반 도서 목록 표시
- Intent를 통한 화면 간 도서 데이터 전달
- Toolbar + 옵션 메뉴 (도서목록, 마이페이지 등 2개 이상)
- NavigationDrawer를 활용한 사이드 메뉴
- 화면 간 이동 및 뒤로가기 처리

### 가산점 기능
- **장바구니 시스템**: 도서 담기, 수량 증가, 모든 Toolbar에 장바구니 아이콘과 실시간 뱃지 표시
- **E-Book 무료 미리보기**: 도서별 5페이지 무료 열람, ViewPager2 기반 책장 넘김 애니메이션, 5페이지 초과 시 안내 메시지 표시

## 개발 환경

| 항목 | 내용 |
|------|------|
| 언어 | Kotlin |
| IDE | Android Studio |
| minSdk | 24 (Android 7.0 Nougat) |
| targetSdk | 36 (Android 16) |
| 빌드 기능 | ViewBinding |

## 주요 라이브러리

### UI 컴포넌트
- `androidx.core:core-ktx`
- `androidx.appcompat:appcompat`
- `androidx.constraintlayout:constraintlayout`
- `androidx.recyclerview:recyclerview`
- `androidx.cardview:cardview`
- `androidx.drawerlayout:drawerlayout`
- `androidx.viewpager2:viewpager2`
- `androidx.fragment:fragment-ktx`

### Material Design
- `com.google.android.material:material`

## 프로젝트 구조

```
app/
├── java/com/example/bookapp/
│   ├── SplashActivity.kt
│   ├── MainActivity.kt
│   ├── BookListActivity.kt
│   ├── BookDetailActivity.kt
│   ├── BookAdapter.kt
│   ├── Book.kt
│   ├── CartManager.kt          # 장바구니 싱글톤
│   ├── CartItem.kt
│   ├── CartAdapter.kt
│   ├── CartActivity.kt
│   ├── CartBadgeHelper.kt      # 공통 뱃지 갱신 헬퍼
│   ├── EBookListActivity.kt
│   ├── EBookReaderActivity.kt
│   ├── EBookPagerAdapter.kt
│   ├── EBookPageFragment.kt
│   ├── EBookSample.kt
│   └── BookFlipPageTransformer.kt   # 책장 넘김 애니메이션
└── res/
    ├── layout/
    ├── menu/
    ├── drawable/
    └── values/
```

## 핵심 구현 포인트

### 장바구니 상태 관리 (CartManager)
데이터베이스나 별도 저장소 없이도, Kotlin의 `object` 선언 하나로 앱이 실행되는 동안 모든 화면이 동일한 장바구니 상태를 공유하도록 구현했습니다. 각 화면마다 따로 상태를 동기화하는 코드를 작성할 필요가 없어 데이터 일관성을 보장하고, 메모리 효율도 확보했습니다.

### 책장 넘김 애니메이션 (BookFlipPageTransformer)
ViewPager2의 기본 페이지 전환은 단순한 좌우 슬라이드뿐이라, `PageTransformer`를 직접 구현하여 Y축 회전, 스케일 축소, 투명도 변화를 페이지 진행도에 따라 동시에 적용했습니다. 이전 페이지는 고정하고 다음 페이지만 책의 제본 부분을 축으로 회전하며 덮어오도록 설계해, 실제 책장을 넘기는 듯한 입체감을 구현했습니다.

## 실행 방법

1. Android Studio에서 프로젝트 열기
2. Gradle Sync 진행
3. 에뮬레이터 또는 실기기에서 Run

## AI 사용 내역

Claude를 활용하여 코드 구현, 디버깅, 발표 자료 구성에 도움을 받았습니다.
