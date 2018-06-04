package com.parapaparam.chiphy.entity

import com.google.gson.annotations.SerializedName


data class Pagination constructor(
        @SerializedName("total_count") val totalCount: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("offset") val offset: Int
)