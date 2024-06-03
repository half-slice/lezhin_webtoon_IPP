package com.example.lezhin_webtoon_ipp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.databinding.FragmentContentsRankBinding
import com.example.lezhin_webtoon_ipp.databinding.FragmentGiftBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class GiftFragment : Fragment() {
    private var _binding: FragmentGiftBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGiftBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val now = System.currentTimeMillis()
        var day = Date(now)

        val calendar = Calendar.getInstance()
        calendar.time = day
        calendar.add(Calendar.DATE, 5)

        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val time = sdf.format(day)

        //기간 설정
        binding.giftPeriodDayText.text = time
        binding.giftPeriodDayText2.text = time
        binding.giftPeriodDayText3.text = time


    }


}