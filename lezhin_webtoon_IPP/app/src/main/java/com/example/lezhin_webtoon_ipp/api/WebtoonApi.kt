package com.example.lezhin_webtoon_ipp.api

import com.example.lezhin_webtoon_ipp.data.WebtoonPojo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebtoonApi {
    @GET("/")
    fun getWebtoon(
        @Query("page") page: Int?,
        @Query("perPage") perPage: Int?,
        @Query("service") service: String?,
        @Query("updateDay") updateDay: CharSequence?
    ): Call<WebtoonPojo>
}

//https://korea-webtoon-api.herokuapp.com
//https://korea-webtoon-api.herokuapp.com/?page=3&perPage=1&service=kakao&updateDay=sun
//page default = 0
//perPage default = 다가져옴
//service default = naver
//updateDay defauly = mon