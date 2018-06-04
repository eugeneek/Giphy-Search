package com.parapaparam.chiphy.entity

import com.google.gson.annotations.SerializedName


data class GifObject constructor(
        @SerializedName("images") val images: GifImages,
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String

)

data class GifImages constructor(
        @SerializedName("fixed_width_still") val image_still: GifImage,
        @SerializedName("fixed_width") val image_gif: GifImage
)

data class GifImage constructor(
        @SerializedName("url") val url: String,
        @SerializedName("width") val width: Int,
        @SerializedName("height") val height: Int,
        @SerializedName("size") val size: Int
)
