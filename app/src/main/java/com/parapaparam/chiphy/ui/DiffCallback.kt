package com.parapaparam.chiphy.ui

import android.support.v7.util.DiffUtil

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 04.01.18.
 */
class DiffCallback(
        private val newData: List<GifModel>,
        private val oldData: List<GifModel>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldData.size
    override fun getNewListSize() = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldData[oldItemPosition].id == newData[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldData[oldItemPosition] == newData[newItemPosition]
}