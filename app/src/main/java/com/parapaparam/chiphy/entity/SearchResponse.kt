package com.parapaparam.chiphy.entity

import com.google.gson.annotations.SerializedName


data class SearchResponse constructor(
        @SerializedName("data") val data: List<GifObject>
)