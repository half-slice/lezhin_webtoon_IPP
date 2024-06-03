package com.example.lezhin_webtoon_ipp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lezhin_webtoon_ipp.adapters.ContentsViewPagerAdapter
import com.example.lezhin_webtoon_ipp.databinding.FragmentContentsBinding
import com.example.lezhin_webtoon_ipp.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class ContentsFragment : Fragment() {
    private var _binding: FragmentContentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var serialFragment: ContentsSeriesFragment
    private lateinit var rankFragment: ContentsRankFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serialFragment = ContentsSeriesFragment()
        rankFragment = ContentsRankFragment()
        val fragmentList = listOf(serialFragment, rankFragment)

        //뷰페이저 어댑터에 연결
        startViewPagerAdapter(fragmentList)

        //뷰페이저 스와이프 기능 봉쇄
        binding.fragmentContentsViewpager.isUserInputEnabled = false

        //Tab과 ViewPager를 연결
        TabLayoutMediator(
            binding.categoryTablayout,
            binding.fragmentContentsViewpager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "연재"
                1 -> tab.text = "랭킹"
            }
        }.attach()

    }

    private fun startViewPagerAdapter(fragmentList: List<Fragment>) {
        //ViewPager에 Adapter 연결
        val adapter = ContentsViewPagerAdapter(this)

        adapter.fragments = fragmentList
        binding.fragmentContentsViewpager.adapter = adapter
    }
}


/* 동적으로 탭버튼 만들기
val tabLayout = binding.tabs
val tab1: TabLayout.Tab = tabLayout.newTab()
tab1.text = "Hello1"
tabLayout.addTab(tab1)
*/