package com.example.lezhin_webtoon_ipp.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.databinding.ActivityDetailBinding
import com.example.lezhin_webtoon_ipp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchBackImagebutton.setOnClickListener(){
            Log.d("back button click!", "---------------back button click")
            finish()
        }
    }


}