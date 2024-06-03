package com.example.lezhin_webtoon_ipp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lezhin_webtoon_ipp.adapters.LibraryViewPagerAdapter
import com.example.lezhin_webtoon_ipp.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator


class LibraryFragment : Fragment() {
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    private lateinit var sawFragment: LibrarySeenFragment
    private lateinit var interestFragment: LibraryInterestFragment
    private lateinit var collectFragment: LibraryCollectFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sawFragment = LibrarySeenFragment()
        interestFragment = LibraryInterestFragment()
        collectFragment = LibraryCollectFragment()
        val fragmentList = listOf(sawFragment, interestFragment, collectFragment)

        //뷰페이저 설정
        setLibraryViewPager(fragmentList)

        //Tab과 ViewPager를 연결
        TabLayoutMediator(binding.fragmentLibraryCategoryTablayout, binding.fragmentLibraryViewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "본 작품"
                1 -> tab.text = "찜한 작품"
                2 -> tab.text = "소장 작품"
            }
        }.attach()

    }

    private fun setLibraryViewPager(fragmentList: List<Fragment>) {
        //ViewPager에 Adapter 연결
        val adapter = LibraryViewPagerAdapter(this)

        adapter.fragments = fragmentList
        binding.fragmentLibraryViewpager.adapter = adapter
    }

}