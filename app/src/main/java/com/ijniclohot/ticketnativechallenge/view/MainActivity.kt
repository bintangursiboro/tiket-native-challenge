package com.ijniclohot.ticketnativechallenge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
                else -> return@Observer
            }
        })
    }

    private fun initButton() {

        searchButton.isEnabled = false

        searchButton.setOnClickListener {
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

        userRecyclerView.layoutManager = LinearLayoutManager(this)

        userRecyclerView.adapter = mainAdapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun onSuccessView(dataList: List<GithubUser>) {
        mainAdapter.refreshData(dataList as ArrayList<GithubUser>)
    }

    override fun onErrorView(errorMsg: String) {

    }

    override fun onLoadMoreView() {

    }

    override fun onErrorLoadMore(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }
}