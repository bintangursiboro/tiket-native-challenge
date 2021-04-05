package com.ijniclohot.ticketnativechallenge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ijniclohot.ticketnativechallenge.R
import com.ijniclohot.ticketnativechallenge.base_view.BaseListViewInterface
import com.ijniclohot.ticketnativechallenge.factory.viewmodel.CustomViewModelFactory
import com.ijniclohot.ticketnativechallenge.model.GithubUser
import com.ijniclohot.ticketnativechallenge.model.Status
import com.ijniclohot.ticketnativechallenge.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.github_user_item_layout.*

class MainActivity : AppCompatActivity(), BaseListViewInterface<GithubUser> {
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager

    private fun initView() {
        initViewModel()
        initButton()
        initEditText()
        initRecyclerView()
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this, CustomViewModelFactory())[MainViewModel::class.java]

        mainViewModel.getGithubUserLiveData().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    onSuccessView(it.data!!)
                }
                Status.ERROR -> {
                    onErrorView(it.message!!)
                }
                Status.LOAD_MORE -> {
                    onLoadMoreView()
                }
                Status.LOAD_MORE_ERROR -> {
                    onErrorLoadMore(it.message!!)
                }
                Status.LOADING -> {
                    onLoadingView()
                }
            }
        })
    }

    private fun initButton() {

        searchButton.isEnabled = false

        searchButton.setOnClickListener {
            mainViewModel.retrieveUsers(userEditText.text.toString())
        }

        retryButton.setOnClickListener {
            mainViewModel.retrieveUsers(userEditText.text.toString())
        }
    }

    private fun initEditText() {
        userEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 == null) {
                    searchButton.isEnabled = false
                } else searchButton.isEnabled = p0.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun initRecyclerView() {
        mainAdapter = MainAdapter(ArrayList(), this)

        linearLayoutManager = LinearLayoutManager(this)

        userRecyclerView.layoutManager = linearLayoutManager

        userRecyclerView.adapter = mainAdapter

        userRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = linearLayoutManager.childCount

                    val pastVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition()

                    val total = mainAdapter.itemCount

                    if (mainViewModel.getGithubUserLiveData().value!!.status != Status.LOAD_MORE) {
                        if ((visibleItemCount + pastVisibleItem) >= total) {
                            mainViewModel.loadMoreData()
                        }
                    }

                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun onSuccessView(dataList: List<GithubUser>) {
        userRecyclerView.visibility = View.VISIBLE

        loadProgressBar.visibility = View.GONE

        errorLayout.visibility = View.GONE

        mainAdapter.refreshData(dataList as ArrayList<GithubUser>)
    }

    override fun onErrorView(errorMsg: String) {
        userRecyclerView.visibility = View.GONE

        loadProgressBar.visibility = View.GONE

        errorLayout.visibility = View.VISIBLE

        errorTextView.text = errorMsg
    }

    override fun onLoadMoreView() {
        loadProgressBar.visibility = View.VISIBLE
    }

    override fun onErrorLoadMore(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }

    override fun onLoadingView() {
        loadProgressBar.visibility = View.VISIBLE

        userRecyclerView.visibility = View.GONE

        errorLayout.visibility = View.GONE
    }
}