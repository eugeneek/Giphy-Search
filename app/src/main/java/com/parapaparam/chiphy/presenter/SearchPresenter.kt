package com.parapaparam.chiphy.presenter

import com.arellomobile.mvp.InjectViewState
import com.parapaparam.chiphy.App
import com.parapaparam.chiphy.SchedulersProvider
import com.parapaparam.chiphy.data.GiphyRepository
import com.parapaparam.chiphy.entity.SearchResponse
import com.parapaparam.chiphy.ui.GifModel
import com.parapaparam.chiphy.view.SearchView
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit


@InjectViewState
class SearchPresenter (
        private val giphyRepository: GiphyRepository,
        private val schedulersProvider: SchedulersProvider
): BasePresenter<SearchView>() {

    private var screenWidth: Int = 0
    private var lastSearchText = ""
    private var data = mutableListOf<GifModel>()
    private val searchTextSubject: PublishSubject<String> = PublishSubject.create()
    private val endlessScrollSubject: BehaviorSubject<Int> = BehaviorSubject.create()

    override fun onFirstViewAttach() {
        endlessScrollSubject.onNext(0)

        Observable.combineLatest(
                searchTextSubject
                        .subscribeOn(schedulersProvider.io())
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .filter({ it.length >= 2})
                        .distinctUntilChanged()
                        .observeOn(schedulersProvider.ui())
                        .doOnNext({
                            viewState.showLoaderView()
                            viewState.clearList()
                            data.clear()
                        }),
                endlessScrollSubject,
                BiFunction<String, Int, Pair<String, Int>> { query, offset -> Pair(query, offset) }
        )
                .observeOn(schedulersProvider.io())
                .switchMapSingle({
                    lastSearchText = it.first
                    giphyRepository.search(it.first, App.PAGE_SIZE, it.second)
                            .observeOn(schedulersProvider.ui())
                            .onErrorReturn { t ->
                                Timber.e(t)
                                viewState.showErrorMessage(t.message ?: "Unexpected error")
                                SearchResponse(data = emptyList())
                             }
                })
                .observeOn(schedulersProvider.ui())
                .subscribe(
                        { response -> onResponse(response) },
                        { t -> onError(t) }
                )
                .connect()
    }

    fun onSearchTextChanged(newText: String) {
        searchTextSubject.onNext(newText)
    }

    fun onNeedMore(currentSize: Int) {
        endlessScrollSubject.onNext(currentSize)
    }

    private fun onError(t: Throwable) {
        viewState.showErrorView()
        Timber.e(t)
    }

    private fun onResponse(response: SearchResponse) {
        Timber.i("onResponse()")
//        Timber.d("Response: $response")
        val newData = response.data.map {
            GifModel(id = it.id,
                    width = it.images.image_gif.width,
                    height = it.images.image_gif.height,
                    size = it.images.image_gif.size,
                    urlImageStill = it.images.image_still.url,
                    urlImageGif = it.images.image_gif.url) }

        data.addAll(newData)
        if (!data.isEmpty()) {
            viewState.showData(data)
        } else {
            viewState.showNotFoundView()
        }

        viewState.setupToolbar(lastSearchText)
    }
}