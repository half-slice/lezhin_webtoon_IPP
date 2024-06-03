package com.example.lezhin_webtoon_ipp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lezhin_webtoon_ipp.fragments.ContentsFragment
import com.example.lezhin_webtoon_ipp.fragments.ContentsRankFragment
import com.example.lezhin_webtoon_ipp.fragments.ContentsSeriesFragment

class ContentsViewPagerAdapter(fragment: ContentsFragment): FragmentStateAdapter(fragment) {
    var fragments = listOf<Fragment>()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ContentsSeriesFragment()
            1 -> ContentsRankFragment()
            else -> ContentsSeriesFragment()
        }
    }
}