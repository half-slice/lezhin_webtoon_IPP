package com.example.lezhin_webtoon_ipp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lezhin_webtoon_ipp.adapters.ContentsSeriesListAdapter
import com.example.lezhin_webtoon_ipp.api.WebtoonApiManager
import com.example.lezhin_webtoon_ipp.databinding.FragmentContentsSeriesBinding
import com.google.android.material.tabs.TabLayout

class ContentsSeriesFragment : Fragment() {
    //메모리 누수를 방지하면서 ViewBinding 객체에 안전하게 접근하기 위해 사용됩니다.
    private var _binding: FragmentContentsSeriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var webtoonApiManager: WebtoonApiManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentsSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //탭레이아웃 간격
        setTabItemMargin(binding.daysTablayout)

        //연재 레이아웃
        webtoonApiManager = WebtoonApiManager()
        setSeriesLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //각 리사이클러뷰 설정
    fun setSeriesLayout(){
        showWebtoonRecyclerView(1, 13, "kakao", "mon")
        binding.daysTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //탭이 선택될때
                selectTabItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //탭이 선택되지 않은 상태로 변경 되었을 때
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //이미 선택된 탭이 다시 선택 되었을 때
            }
        })
    }

    //탭 선택될시 해당 api호출 후 출력
    fun selectTabItem(position: Int){
        when(position){
            0 -> showWebtoonRecyclerView(1, 11, "kakao", "mon")
            1 -> showWebtoonRecyclerView(1, 12, "kakao", "tue")
            2 -> showWebtoonRecyclerView(1, 14, "kakao", "wed")
            3 -> showWebtoonRecyclerView(1, 13, "kakao", "thu")
            4 -> showWebtoonRecyclerView(1, 11, "kakao", "fri")
            5 -> showWebtoonRecyclerView(1, 12, "kakao", "sat")
            6 -> showWebtoonRecyclerView(1, 13, "kakao", "sun")
        }
    }

    //api로 응답받아서 해당 리사이클러뷰에 출력
    fun showWebtoonRecyclerView(pageNum: Int, perPageNum: Int, servicePlatform: String, update: String){
        webtoonApiManager.getWebtoonList(pageNum, perPageNum, servicePlatform, update) { webtoonList ->
            if (isAdded) {
                binding.fragmentContentsSeriesRecycler.layoutManager = LinearLayoutManager(requireContext())

                val adapter = ContentsSeriesListAdapter()
                binding.fragmentContentsSeriesRecycler.adapter = adapter
                adapter.submitList(webtoonList)
            } else {
                // 프래그먼트가 연결되지 않은 경우 처리할 로직을 여기에 작성합니다.
            }
        }
    }

    private fun setTabItemMargin(tabLayout: TabLayout) {
        val tabs = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount) {
            val tab = tabs.getChildAt(i)
            val lp = tab.layoutParams as LinearLayout.LayoutParams
            if (i == 0) {
                lp.marginStart = 30
            }
            lp.marginEnd = 30
            tab.layoutParams = lp
            tabLayout.requestLayout()
        }
    }

}