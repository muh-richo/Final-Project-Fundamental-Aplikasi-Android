package com.example.githubuserapp.ui.detailuser

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.response.DetailUserResponse
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val user = intent.getParcelableExtra<ItemsItem>(EXTRA_USER)
        val current_user = user?.login.toString()

        viewModel.getUser(current_user)

        viewModel.listDetail.observe(this) {
            setShowUser(it)
        }

        setViewPager(current_user)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setShowUser(listUser: DetailUserResponse) {
        Glide.with(this@DetailUserActivity)
            .load(listUser.avatarUrl)
            .circleCrop()
            .into(binding.ivProfil)
        binding.tvNama.text = listUser.name
        binding.tvUsername.text = listUser.login
        binding.tvFollowers.text = "${listUser.followers} followers"
        binding.tvFollowing.text = "${listUser.following} following"
    }

    private fun setViewPager(curr_user: String) {
        val sectionPagerAdapter = SectionsPagerAdapter(this)
        sectionPagerAdapter.username = curr_user
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, positon ->
            tab.text = resources.getString(TAB_TITLES[positon])
        }.attach()
    }

    fun onBackButtonClicked(view: View) {
        onBackPressed()
    }
}