package com.example.lezhin_webtoon_ipp.api

import android.content.Context
import com.example.lezhin_webtoon_ipp.data.WebtoonItem
import com.example.lezhin_webtoon_ipp.data.WebtoonPojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebtoonApiManager(private val context: Context) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://korea-webtoon-api.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val webtoonApi: WebtoonApi = retrofit.create(WebtoonApi::class.java)

    fun getWebtoonList(pageNum: Int, perPageNum: Int, servicePlatform: String, update: String, callback: (List<WebtoonItem>) -> Unit) {
        webtoonApi.getWebtoon(
            page = pageNum,
            perPage = perPageNum,
            service = servicePlatform,
            updateDay = update
        ).enqueue(object : Callback<WebtoonPojo> {
            override fun onResponse(call: Call<WebtoonPojo>, response: Response<WebtoonPojo>) {
                if (response.isSuccessful) {
                    val webtoonPojo = response.body()
                    webtoonPojo?.let { pojo ->
                        val webtoonList = pojo.webtoons
                        callback(webtoonList)
                    }
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<WebtoonPojo>, t: Throwable) {
                // Handle failure
                t.printStackTrace()
            }
        })
    }
}
