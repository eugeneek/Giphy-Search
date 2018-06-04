package com.parapaparam.chiphy.ui

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.parapaparam.chiphy.R


class SearchAdapter(
        private val screenWidth: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ITEM = 1
        const val TYPE_LOADER = 0
    }

    private val data: MutableList<GifModel> = mutableListOf()

    var isLoading: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_ITEM -> GifViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false),
                    screenWidth)

            else -> LoaderViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false))
        }
    }

    override fun getItemCount() = if (isLoading) data.size + 1 else data.size

    override fun getItemViewType(position: Int): Int {
        return if (isLoading and (position == data.size)) TYPE_LOADER else TYPE_ITEM
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GifViewHolder -> holder.bind(data[position])
            is LoaderViewHolder -> holder.bind()
        }
    }

    fun setData(data: List<GifModel>) {
        val oldData = this.data.toList()
        this.data.clear()
        this.data.addAll(data)

        DiffUtil.calculateDiff(DiffCallback(data, oldData))
                .dispatchUpdatesTo(this)
    }
}