package com.parapaparam.chiphy.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.parapaparam.chiphy.ui.GifModel

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchView : MvpView {
    fun showNotFoundView()
    fun showLoaderView()
    fun showErrorView()
    fun clearList()
    fun showData(data: List<GifModel>)
    fun setupToolbar(text: String)
    fun showErrorMessage(message: String)
}