package com.example.githubuserapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.ui.viewmodelfactory.ThemeViewModelFactory
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.detailuser.DetailUserActivity
import com.example.githubuserapp.ui.favourite.FavouriteUserActivity
import com.example.githubuserapp.ui.settings.SettingActivity
import com.example.githubuserapp.ui.settings.SettingPreferences
import com.example.githubuserapp.ui.settings.datastore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.datastore)
        viewModel = ViewModelProvider(this@MainActivity, ThemeViewModelFactory(pref)).get(HomeViewModel::class.java)

        switchTheme(viewModel)
        search(binding)
        showRecyclerList()

        viewModel.listUser.observe(this) {
            setUserData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun switchTheme(viewModel: HomeViewModel) {
        viewModel.getThemeSetting().observe(this@MainActivity) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_favourite -> {
                val intent = Intent(this@MainActivity, FavouriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
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
        val moveWithParcelableIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
        moveWithParcelableIntent.putExtra(DetailUserActivity.EXTRA_USER, data.login)
        startActivity(moveWithParcelableIntent)
    }
}