package com.parapaparam.chiphy.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import com.parapaparam.chiphy.GlideApp
import com.parapaparam.chiphy.R
import kotlinx.android.synthetic.main.item_gif.view.*

class GifViewHolder(
        itemView: View,
        private val screenWidth: Int
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var isPlaying = false

    override fun onClick(v: View) {
        isPlaying = !isPlaying
        if (isPlaying) play(v.tag as GifModel) else stop(v.tag as GifModel)
    }

    fun bind(item: GifModel) {
        itemView.tag = item
        itemView.setOnClickListener(this)

        val size = calculateSize(item.width, item.height)
        val params = FrameLayout.LayoutParams(size.first, size.second)
        itemView.ivGif.layoutParams = params
        itemView.flPlay.layoutParams = params

        itemView.tvSize.text = itemView.resources.getString(R.string.gif_size, item.size / 1024)
        isPlaying = false
        stop(item)
    }

    private fun play(item: GifModel) {
        itemView.flPlay.visibility = View.INVISIBLE
        loadImage(item.urlImageGif)
    }

    private fun stop(item: GifModel) {
        itemView.flPlay.visibility = View.VISIBLE
        loadImage(item.urlImageStill)
    }

    private fun loadImage(url: String) {
        GlideApp.with(itemView)
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(itemView.ivGif)
    }

    private fun calculateSize(imageWidth: Int, imageHeight: Int): Pair<Int, Int> {
        val width = screenWidth / 2
        val height = imageHeight * width / imageWidth

        return Pair(width, height)
    }
}