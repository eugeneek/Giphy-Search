package com.parapaparam.chiphy.ui


import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.parapaparam.chiphy.App
import com.parapaparam.chiphy.R
import com.parapaparam.chiphy.SchedulersProvider
import com.parapaparam.chiphy.data.GiphyRepository
import com.parapaparam.chiphy.presenter.SearchPresenter
import com.parapaparam.chiphy.view.SearchView
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject


class SearchFragment : MvpAppCompatFragment(), SearchView {

    @Inject lateinit var giphyRepository: GiphyRepository
    @Inject lateinit var schedulersProvider: SchedulersProvider

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    @ProvidePresenter
    fun providePresenter(): SearchPresenter {
        return SearchPresenter(giphyRepository, schedulersProvider)
    }

    private lateinit var adapter: SearchAdapter
    private lateinit var scrollListener: InfiniteScrollListener
    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)

        initViews()
    }

    override fun showNotFoundView() {
        ivMessage.setImageResource(R.drawable.ic_no_found)
        tvMessage.setText(R.string.gif_search_no_found)
        rlMessageView.visibility = View.VISIBLE
        rlLoaderView.visibility = View.GONE
        rvGif.visibility = View.GONE
    }

    override fun showLoaderView() {
        rlLoaderView.visibility = View.VISIBLE
        rlMessageView.visibility = View.GONE
    }

    override fun showErrorView() {
        ivMessage.setImageResource(R.drawable.ic_error)
        tvMessage.setText(R.string.gif_search_error)
        rlMessageView.visibility = View.VISIBLE
        rlLoaderView.visibility = View.GONE
        rvGif.visibility = View.GONE
    }

    override fun clearList() {
        adapter.setData(emptyList())
        scrollListener.reset()
    }

    override fun showData(data: List<GifModel>) {
        adapter.setData(data)
        rvGif.visibility = View.VISIBLE
        rlMessageView.visibility = View.GONE
        rlLoaderView.visibility = View.GONE
    }

    override fun showErrorMessage(message: String) {
        snackbar.setText(message).show()
    }

    override fun setupToolbar(text: String) {
        if (!text.isEmpty()) {
            toolbar.title = text
        } else {
            toolbar.title = getString(R.string.app_name)
        }
    }

    private fun initViews() {
        // Setup Toolbar SearchView
        toolbar.inflateMenu(R.menu.search)

        val searchView = (toolbar.menu.findItem(R.id.menu_search).actionView as android.support.v7.widget.SearchView)
        searchView
                .setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        presenter.onSearchTextChanged(newText)
                        return true
                    }
                })

        // Setup RecyclerView
        val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        rvGif.layoutManager = layoutManager

        val displayMetrics = DisplayMetrics()
        (App.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay.getMetrics(displayMetrics)
        adapter = SearchAdapter(displayMetrics.widthPixels)

        rvGif.adapter = adapter

        scrollListener = InfiniteScrollListener(object : NeedMoreListener {
            override fun onNeedMore(currentSize: Int) {
                presenter.onNeedMore(currentSize)
            }
        }, layoutManager)

        rvGif.addOnScrollListener(scrollListener)
    }
}
