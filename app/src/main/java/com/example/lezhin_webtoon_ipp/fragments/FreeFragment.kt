package com.example.lezhin_webtoon_ipp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.activities.MainActivity
import com.example.lezhin_webtoon_ipp.adapters.ContentsRankListAdapter
import com.example.lezhin_webtoon_ipp.adapters.FreeListAdapter
import com.example.lezhin_webtoon_ipp.adapters.FreeWaitListAdapter
import com.example.lezhin_webtoon_ipp.api.WebtoonApi
import com.example.lezhin_webtoon_ipp.api.WebtoonApiManager
import com.example.lezhin_webtoon_ipp.data.WebtoonPojo
import com.example.lezhin_webtoon_ipp.databinding.FragmentFreeBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FreeFragment : Fragment() {
    private var _binding: FragmentFreeBinding? = null
    private val binding get() = _binding!!
    private lateinit var webtoonApiManager: WebtoonApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainStickyView.run {
            header = binding.headerView

        }

        //툴바 설정
        setToolbar()

        //wait_free 리사이클러뷰 설정
        setWaitFreeRecyclerView()

        //탭레이아웃 간격 설정
        setTabItemMargin()

        //무료 리사이클러뷰 설정
        webtoonApiManager = WebtoonApiManager()
        setFreeRecyclerView()

    }

    private fun getBannerList(): List<Int> {
        return listOf(
            R.drawable.wait_free_img1,
            R.drawable.wait_free_img2,
            R.drawable.wait_free_img3,
            R.drawable.wait_free_img4,
            R.drawable.wait_free_img5
        )
    }

    fun setWaitFreeRecyclerView(){
        binding.fragmentWaitFreeRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        val adapter = FreeWaitListAdapter()
        val bannerList = getBannerList()
        binding.fragmentWaitFreeRecycler.adapter = adapter
        adapter.submitList(bannerList)
    }

    private fun setTabItemMargin() {
        val tabs = binding.headerTablayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount) {
            val tab = tabs.getChildAt(i)
            val lp = tab.layoutParams as LinearLayout.LayoutParams
            if (i == 0) {
                lp.marginStart = 20
            }
            lp.marginEnd = 20
            tab.layoutParams = lp
            binding.headerTablayout.requestLayout()
        }
    }

    //리사이클러뷰 설정
    //setFreeRecyclerView -> selectTabItem -> setWebtoobList -> getWebtoonList -> 출력
    fun setFreeRecyclerView(){
        showWebtooRecyclerView(1, 20, "kakao", "mon")
        binding.headerTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
            0 -> showWebtooRecyclerView(1, 20, "kakao", "mon")
            1 -> showWebtooRecyclerView(2, 20, "kakao", "mon")
            2 -> showWebtooRecyclerView(3, 20, "kakao", "mon")
            3 -> showWebtooRecyclerView(4, 20, "kakao", "mon")
            4 -> showWebtooRecyclerView(5, 20, "kakao", "mon")
            5 -> showWebtooRecyclerView(5, 20, "kakao", "tue")
            6 -> showWebtooRecyclerView(4, 20, "kakao", "wed")
            7 -> showWebtooRecyclerView(3, 20, "kakao", "thu")
            8 -> showWebtooRecyclerView(2, 20, "kakao", "fri")
            9 -> showWebtooRecyclerView(1, 20, "kakao", "sat")
            10 -> showWebtooRecyclerView(1, 20, "kakao", "sun")
        }
    }

    //webtoonApiManager에서 웹툰리스트를 가져와서 리사이클러뷰에 출력
    fun showWebtooRecyclerView(pageNum: Int, perPageNum: Int, servicePlatform: String, update: String){

        webtoonApiManager.getWebtoonList(pageNum, perPageNum, servicePlatform, update) { webtoonList ->
            if (isAdded) {
                val layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
                binding.fragmentFreeRecycler.layoutManager = layoutManager

                val adapter = FreeListAdapter()
                binding.fragmentFreeRecycler.adapter = adapter
                adapter.submitList(webtoonList)
            } else {
                // 프래그먼트가 연결되지 않은 경우 처리할 로직을 여기에 작성합니다.
            }
        }
    }

    fun setToolbar(){
        val toolbar = (activity as MainActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
    }

}