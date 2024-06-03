package com.example.lezhin_webtoon_ipp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.adapters.HomeBannerAdapter
import com.example.lezhin_webtoon_ipp.adapters.MainBottomBannerAdapter
import com.example.lezhin_webtoon_ipp.databinding.ActivityMainBinding
import kotlin.math.ceil


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var currentPosition = Int.MAX_VALUE / 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //툴바 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)             //툴바 제목 안보이게
        supportActionBar?.setDisplayHomeAsUpEnabled(true)               //메뉴 왼쪽 홈 메뉴 버튼 표시,    기본 아이콘은 뒤로가기
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu1)     //메뉴 아이콘 설정

        //하단 네비게이션 설정
        binding.apply {
            val host = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
            val navController = host.navController
            binding.mainBottomNavigationbar.setupWithNavController(navController)
        }
    }

    //툴바 메뉴 버튼을 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)         // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
        return true
    }

    //툴바의 아이템이 선택될때 실행
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {  //메뉴 버튼
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }

            R.id.search_item -> {   //검색 버튼
                val intent = Intent(binding.root.context, SearchActivity::class.java)
                binding.root.context.startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //배너 이미지 리스트 생성
    private fun getBannerList(): List<Int> {
        return listOf(
            R.drawable.banner_bottom1,
            R.drawable.banner_bottom2,
            R.drawable.banner_bottom3
        )
    }
}