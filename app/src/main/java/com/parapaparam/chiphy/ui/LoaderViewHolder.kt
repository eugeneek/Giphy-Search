package com.parapaparam.chiphy.ui

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View



class LoaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind() {
        val layoutParams = itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.isFullSpan = true
    }
}