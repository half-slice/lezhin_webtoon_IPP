package com.example.lezhin_webtoon_ipp.data
import com.google.gson.annotations.SerializedName

data class WebtoonItemSmall(
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("url") val url: String,
    @SerializedName("img") val img: String,
    @SerializedName("additional") val additional: Additional){
    data class Additional(
        @SerializedName("new") val isNew: Boolean,
        @SerializedName("isRest") val isRest: Boolean,
        @SerializedName("isUp") val isUp: Boolean,
    )
}

