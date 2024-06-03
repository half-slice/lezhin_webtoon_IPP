package com.example.lezhin_webtoon_ipp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 웹 설정 가져오기
        val webSettings: WebSettings = binding.detailWebview.getSettings()

        // JavaScript를 활성화하여 웹 페이지에서 JavaScript를 사용할 수 있게 합니다.
        webSettings.javaScriptEnabled = true

        // 파일 접근을 허용하여 웹 페이지에서 로컬 파일에 접근할 수 있게 합니다.
        webSettings.allowFileAccess = true

        // 콘텐츠 접근을 허용하여 웹 페이지에서 콘텐츠에 접근할 수 있게 합니다.
        webSettings.allowContentAccess = true

        // DOM 스토리지를 활성화하여 웹 페이지에서 LocalStorage 및 SessionStorage를 사용할 수 있게 합니다.
        webSettings.domStorageEnabled = true

        // 데이터베이스 스토리지를 활성화하여 웹 페이지에서 웹 SQL 데이터베이스 API를 사용할 수 있게 합니다.
        webSettings.databaseEnabled = true

        // 캐시 모드를 설정하여 웹 페이지의 리소스를 캐시에서 불러오도록 합니다.
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT

        // 혼합된 콘텐츠 모드를 설정하여 HTTPS 페이지에서 HTTP 리소스를 불러올 수 있게 합니다.
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        // 인텐트에서 URL 가져와서 웹뷰에 로드
        val url = intent.getStringExtra("URL")
        if (url != null) {
            Log.d("DetailActivity", "URL: $url")
            binding.detailWebview.loadUrl(url)
        } else {
            Log.e("DetailActivity", "URL is null")
        }

        //뒤로 가기
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if (binding.detailWebview.canGoBack()) {
                binding.detailWebview.goBack();  // 웹뷰 내에서 뒤로 갈 수 있으면 뒤로 감
            } else {
                finish()                            // 그 외의 경우 기본 뒤로 가기 동작
            }
        }
    }


}