package com.example.lezhin_webtoon_ipp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lezhin_webtoon_ipp.adapters.ContentsRankListAdapter
import com.example.lezhin_webtoon_ipp.api.WebtoonApiManager
import com.example.lezhin_webtoon_ipp.databinding.FragmentContentsRankBinding
import com.google.android.material.tabs.TabLayout

class ContentsRankFragment : Fragment() {
    //메모리 누수를 방지하면서 ViewBinding 객체에 안전하게 접근하기 위해 사용됩니다.
    private var _binding: FragmentContentsRankBinding? = null
    private val binding get() = _binding!!
    private lateinit var webtoonApiManager: WebtoonApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentsRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //탭레이아웃 간격
        setTabItemMargin(binding.genreTablayout)

        //실시간 랭킹
        webtoonApiManager = WebtoonApiManager(requireContext())
        setRecyclerView(binding.currentRakingRecyclerview, 1)

        //신작 랭킹
        setRecyclerView(binding.newRankingRecyclerview, 2)

        //이벤트 랭킹
        setRecyclerView(binding.eventRankingRecyclerview, 3)

        //2023년 랭킹
        setRecyclerView(binding.yearRankingRecyclerview, 4)

    }

    //각 리사이클러뷰 설정
    fun setRecyclerView(recyclerView: RecyclerView, pageNum: Int){
        showWebtoonRecyclerView(pageNum, 9, "kakao", "mon", recyclerView)
        binding.genreTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //탭이 선택될때
                selectTabItem(tab.position, recyclerView, pageNum)
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
    fun selectTabItem(position: Int, recyclerView: RecyclerView, pageNum: Int){
        when(position){
            0 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "mon", recyclerView)
            1 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "mon", recyclerView)
            2 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "mon", recyclerView)
            3 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "mon", recyclerView)
            4 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "mon", recyclerView)
            5 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "tue", recyclerView)
            6 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "wed", recyclerView)
            7 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "thu", recyclerView)
            8 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "fri", recyclerView)
            9 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "sat", recyclerView)
            10 -> showWebtoonRecyclerView(pageNum, 9, "kakao", "sun", recyclerView)
        }
    }

    //api로 응답받아서 해당 리사이클러뷰에 출력
    fun showWebtoonRecyclerView(pageNum: Int, perPageNum: Int, servicePlatform: String, update: String, recyclerView: RecyclerView){
        webtoonApiManager.getWebtoonList(pageNum, perPageNum, servicePlatform, update) { webtoonList ->
            if (isAdded) {
                val layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.HORIZONTAL, false)
                layoutManager.spanSizeLookup = object  : GridLayoutManager.SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int {
                        return 1
                    }
                }
                recyclerView.layoutManager = layoutManager

                val adapter = ContentsRankListAdapter()
                recyclerView.adapter = adapter
                adapter.submitList(webtoonList)
            } else {
                // 프래그먼트가 연결되지 않은 경우 처리할 로직을 여기에 작성합니다.
            }
        }
    }

    //탭 간격 설정
    private fun setTabItemMargin(tabLayout: TabLayout) {
        val tabs = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount) {
            val tab = tabs.getChildAt(i)
            val lp = tab.layoutParams as LinearLayout.LayoutParams
            if (i == 0) {
                lp.marginStart = 15
            }
            lp.marginEnd = 15
            tab.layoutParams = lp
            tabLayout.requestLayout()
        }
    }

}