package com.parapaparam.chiphy.ui

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager


class InfiniteScrollListener(
        private val needMoreListener: NeedMoreListener,
        val layoutManager: StaggeredGridLayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 10
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount

            var firstVisibleItems: IntArray? = null
            firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems)
            if (firstVisibleItems.isEmpty())
                return
            firstVisibleItem = firstVisibleItems[0]

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                needMoreListener.onNeedMore(totalItemCount)
                loading = true
            }
        }
    }

    fun reset() {
        previousTotal = 0
    }

}