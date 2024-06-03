package com.example.lezhin_webtoon_ipp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lezhin_webtoon_ipp.fragments.LibraryCollectFragment
import com.example.lezhin_webtoon_ipp.fragments.LibraryFragment
import com.example.lezhin_webtoon_ipp.fragments.LibraryInterestFragment
import com.example.lezhin_webtoon_ipp.fragments.LibrarySeenFragment

class LibraryViewPagerAdapter(fragment: LibraryFragment): FragmentStateAdapter(fragment) {
    var fragments = listOf<Fragment>()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> LibrarySeenFragment()
            1 -> LibraryInterestFragment()
            2 -> LibraryCollectFragment()
            else -> LibrarySeenFragment()
        }
    }
}