package com.example.lezhin_webtoon_ipp.data

import com.google.gson.annotations.SerializedName

data class WebtoonPojo(
    @SerializedName("webtoons") val webtoons: List<WebtoonItem>
)

