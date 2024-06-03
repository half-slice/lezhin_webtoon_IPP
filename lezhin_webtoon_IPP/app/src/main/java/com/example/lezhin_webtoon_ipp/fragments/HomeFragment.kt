package com.example.lezhin_webtoon_ipp.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.activities.DetailActivity
import com.example.lezhin_webtoon_ipp.adapters.ContentsRankListAdapter
import com.example.lezhin_webtoon_ipp.api.WebtoonApi
import com.example.lezhin_webtoon_ipp.adapters.HomeBannerAdapter
import com.example.lezhin_webtoon_ipp.adapters.HomeRankingListAdapter
import com.example.lezhin_webtoon_ipp.adapters.HomeShortListAdapter
import com.example.lezhin_webtoon_ipp.adapters.HomeWideListAdapter
import com.example.lezhin_webtoon_ipp.api.WebtoonApiManager
import com.example.lezhin_webtoon_ipp.data.WebtoonItem
import com.example.lezhin_webtoon_ipp.data.WebtoonPojo
import com.example.lezhin_webtoon_ipp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.ceil

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var currentPosition = Int.MAX_VALUE / 2 - ceil(getBannerList().size.toDouble() / 2).toInt()
    private var myHandler = MyHandler()
    private lateinit var webtoonApiManager: WebtoonApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //fragment가 처음으로 레이아웃 생성하고 초기화할때 호출
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    //onCreateView에서 반환된 View가 생성되고 삽입된 후에 호출됨
    //fragment의 layout에 대한 추가적인 작업을 수행
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //배너 뷰페이저
        setHomeBanner()

        //indicator 설정
        binding.bannerIndicator.createIndicators(getBannerList().size, 0)
        //binding.bannerIndicator.setViewPager(binding.bannerViewpager)

        //뷰페이저의 자동스크롤을 제어
        binding.bannerViewpager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when(state){
                        //뷰페이저에서 손 떼었을대 / 뷰페이저 멈춰있을 때 -> 자동 스크롤 발동
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart()

                        //뷰페이저 움직이는 중 -> 자동스크롤 멈춤
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()

                        ViewPager2.SCROLL_STATE_SETTLING -> {
                            //
                        }
                    }
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bannerIndicator.animatePageSelected(position % getBannerList().size)
                }
            })
        }

        //장르 설정
        setGenreTabItemMargin(binding.genreTablayout)
        setGenreLayout()

        //랭킹 탭레이아웃 간격 설정
        setRankingTabItemMargin(binding.rankingTablayout)

        //랭킹 레이아웃
        webtoonApiManager = WebtoonApiManager(requireContext())
        setRankingRecyclerView()

        //신작 연재
        showWebtoonRecyclerView(2, 10, "kakao", "mon", binding.newSeriesRecyclerview, HomeWideListAdapter(), false)

        //신규 만화
        showWebtoonRecyclerView(3, 10, "kakao", "mon", binding.newCartoonRecyclerview, HomeShortListAdapter(), false)

        //event 탭
        showWebtoonRecyclerView(4, 10, "kakao", "mon", binding.eventRecyclerview, HomeShortListAdapter(), false)

        //코믹 탭
        showWebtoonRecyclerView(4, 10, "kakao", "wed", binding.comicRecyclerview, HomeShortListAdapter(), false)

        //은꼴 탭
        showWebtoonRecyclerView(5, 10, "kakao", "thu", binding.sexualRecyclerview, HomeShortListAdapter(), false)

        //로맨스 탭
        showWebtoonRecyclerView(4, 10, "kakao", "sun", binding.romanceRecyclerview, HomeShortListAdapter(), false)

        //bl 탭
        showWebtoonRecyclerView(3, 10, "kakao", "sat", binding.highteenBLRecyclerview, HomeShortListAdapter(), false)

        //럽코 탭
        showWebtoonRecyclerView(2, 10, "kakao", "fri", binding.loveComedyRecyclerview, HomeShortListAdapter(), false)

    }

    //배너 뷰페이저를
    private fun setHomeBanner() {
        binding.bannerViewpager.adapter = HomeBannerAdapter(getBannerList())
        binding.bannerViewpager.setCurrentItem(currentPosition, false)

    }

    //자동 스크롤 시작
    private fun autoScrollStart(){
        myHandler.removeMessages(0)
        myHandler.sendEmptyMessageDelayed(0, 3000)
    }

    //자동 스크롤 정지
    private fun autoScrollStop(){
        myHandler.removeMessages(0)
    }

    //자동 스크롤 핸들러
    @SuppressLint("HandlerLeak")
    private inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var currentPosition = binding.bannerViewpager.currentItem
            if(msg.what == 0){
                binding.bannerViewpager.setCurrentItem(++currentPosition, true) //다음 페이지로 이동
                autoScrollStart()   //계속 스크롤한다
            }
        }
    }

    //다른 페이지 갔다가 돌아오면 다시 스크롤 시작
    override fun onResume() {
        super.onResume()
        autoScrollStart()
    }

    //다른 페이지로 떠나있는 동안 스크롤은 정지
    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }

    //랭킹 리사이클러뷰 설정
    fun setRankingRecyclerView(){
        showWebtoonRecyclerView(1, 3, "kakao", "mon", binding.rankingRecycler, HomeRankingListAdapter(), true)
        binding.rankingTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //탭이 선택될때
                selectRankingTabItem(tab.position)
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
    fun selectRankingTabItem(position: Int){
        when(position){
            0 -> showWebtoonRecyclerView(1, 3, "kakao", "mon", binding.rankingRecycler, HomeRankingListAdapter(), true)
            1 -> showWebtoonRecyclerView(2, 3, "kakao", "tue", binding.rankingRecycler, HomeRankingListAdapter(), true)
            2 -> showWebtoonRecyclerView(3, 3, "kakao", "wed", binding.rankingRecycler, HomeRankingListAdapter(), true)
        }
    }

    //api로 응답받아서 해당 리사이클러뷰에 출력
    fun showWebtoonRecyclerView(pageNum: Int, perPageNum: Int, servicePlatform: String, update: String, recyclerView:RecyclerView, adapter : ListAdapter<WebtoonItem, *>,  isRank: Boolean){
        webtoonApiManager.getWebtoonList(pageNum, perPageNum, servicePlatform, update) { webtoonList ->
            if (isAdded) {
                if(isRank){
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                }
                else{
                    recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                }

                recyclerView.adapter = adapter
                adapter.submitList(webtoonList)
            } else {
                // 프래그먼트가 연결되지 않은 경우 처리할 로직을 여기에 작성합니다.
            }
        }
    }

    //배너 이미지 리스트 생성
    private fun getBannerList(): List<Int> {
        return listOf(
            R.drawable.banner_home_upper1,
            R.drawable.banner_home_upper2,
            R.drawable.banner_home_upper3,
            R.drawable.banner_home_upper4,
            R.drawable.banner_home_upper5
        )
    }

    private fun selectGenreTabItem(url: String){
        val intent = Intent(binding.root.context, DetailActivity::class.java)
        intent.putExtra("URL", url)
        binding.root.context.startActivity(intent)
    }

    private fun setGenreLayout(){
        binding.genreTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //탭이 선택될때
                when(tab.position){
                    0 -> selectGenreTabItem("https://www.lezhin.com/ko/bookshome?genre=_all")
                    1 ->  selectGenreTabItem("https://www.lezhin.com/ko/bl")
                    2 ->  selectGenreTabItem("https://www.lezhin.com/ko/romance")
                    3 ->  selectGenreTabItem("https://www.lezhin.com/ko/drama")
                    4 ->  selectGenreTabItem("https://www.lezhin.com/ko/boys")
                    5 ->  selectGenreTabItem("https://www.lezhin.com/ko/sale")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //탭이 선택되지 않은 상태로 변경 되었을 때
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //이미 선택된 탭이 다시 선택 되었을 때
            }
        })
    }

    private fun setGenreTabItemMargin(tabLayout: TabLayout) {
        val tabs = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount) {
            if (i < tabs.childCount){
                val tab = tabs.getChildAt(i)
                val lp = tab.layoutParams as LinearLayout.LayoutParams
                if (i == 0){
                    lp.marginStart = 20
                }
                lp.marginEnd = 20
                tab.layoutParams = lp
                tabLayout.requestLayout()
            }
        }
    }

    //랭킹 탭레이아웃 marginEnd 10dp, padding 10dp
    private fun setRankingTabItemMargin(tabLayout: TabLayout) {
        val tabs = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount) {
            val tab = tabs.getChildAt(i)
            val lp = tab.layoutParams as LinearLayout.LayoutParams
            lp.marginEnd = 20
            tab.layoutParams = lp
            tabLayout.requestLayout()
        }
    }
}