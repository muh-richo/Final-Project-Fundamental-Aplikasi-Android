package com.example.githubuserapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.detailuser.DetailUser

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerList()

        viewModel.listUser.observe(this) {
            setUserData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        search(binding)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showRecyclerList() {
        val layoutInflater = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutInflater
    }

    private fun setUserData(user: List<ItemsItem>) {
        val adapter = UserAdapter(user)
        adapter.submitList(user)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: ItemsItem) {
                showSelectedUser(data)
            }
        })
    }

    private fun search(binding: ActivityMainBinding) {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    viewModel.findUsers(searchView.text.toString())
                    false
                }
        }
    }

    private fun showSelectedUser(data: ItemsItem) {
        val moveWithParcelableIntent = Intent(this@MainActivity, DetailUser::class.java)
        moveWithParcelableIntent.putExtra(DetailUser.EXTRA_USER, data)
        startActivity(moveWithParcelableIntent)
    }
}