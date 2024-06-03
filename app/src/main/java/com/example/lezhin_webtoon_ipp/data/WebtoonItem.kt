package com.example.lezhin_webtoon_ipp.data

import com.google.gson.annotations.SerializedName

data class WebtoonItem(
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("url") val url: String,
    @SerializedName("img") val img: String,
    @SerializedName("service") val service: String,
    @SerializedName("updateDays") val updateDays: List<String>,
    @SerializedName("searchKeyword") val searchKeyword: String,
    @SerializedName("additional") val additional: Additional
){
    data class Additional(
        @SerializedName("new") val isNew: Boolean,
        @SerializedName("isRest") val isRest: Boolean,
        @SerializedName("isUp") val isUp: Boolean,
        @SerializedName("isAdult") val isAdult: Boolean,
        @SerializedName("singularityList") val singularityList: List<Any>
    )
}
