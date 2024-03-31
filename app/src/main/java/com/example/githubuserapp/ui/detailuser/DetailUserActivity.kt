package com.example.githubuserapp.ui.detailuser

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.database.FavouriteUser
import com.example.githubuserapp.data.response.DetailUserResponse
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.ui.favourite.FavouriteUserActivity
import com.example.githubuserapp.ui.viewmodelfactory.FavouriteViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel

    private var buttonState: Boolean = false
    private lateinit var favoriteUser: FavouriteUser
    private var detailUser = DetailUserResponse()

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

        val application = FavouriteViewModelFactory.getInstance(this@DetailUserActivity.application)
        viewModel = ViewModelProvider(this@DetailUserActivity, application).get(DetailViewModel::class.java)

        val user = intent.extras!!.getString(EXTRA_USER)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.getUser(user!!)

        viewModel.listDetail.observe(this) {
            setShowUser(it)
            setFavouriteUser(it)
        }

        setViewPager(user)
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

    private fun insertToDatabase(detailUserList: DetailUserResponse) {
        favoriteUser.let { favUser ->
            favUser.id = detailUserList.id!!
            favUser.avatarUrl = detailUserList.avatarUrl
            favUser.login = detailUserList.login
            favUser.htmlUrl = detailUserList.htmlUrl
            viewModel.insertFavUser(favUser)
            Toast.makeText(this@DetailUserActivity, "User sudah dimasukkan favourite!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFavouriteUser(detailUserResponse: DetailUserResponse) {
        detailUser = detailUserResponse
        setShowUser(detailUserResponse)

        favoriteUser= FavouriteUser(detailUser.id!!, detailUser.avatarUrl, detailUser.login, detailUser.htmlUrl)

        viewModel.getAllFavorites().observe(this) { favoriteUser ->
            if (favoriteUser  != null) {
                for (data in favoriteUser) {
                    if (detailUserResponse.id!! == data.id)  {
                        buttonState = true
                        binding.fabFav.setImageResource(R.drawable.ic_favorite)
                    }
                }
            }
        }

        binding.fabFav.setOnClickListener {
            if (!buttonState) {
                buttonState = true
                binding.fabFav.setImageResource(R.drawable.ic_favorite)
                insertToDatabase(detailUserResponse)

            } else {
                buttonState = false
                binding.fabFav.setImageResource(R.drawable.ic_unfavorite)
                viewModel.deleteFavUser(detailUserResponse.id!!)
                Toast.makeText(this@DetailUserActivity, "User berhasil dihapus dari favourite!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onBackButtonClicked(view: View) {
        onBackPressed()
    }

    fun goToFavourite(view: View){
        val intent = Intent(this@DetailUserActivity, FavouriteUserActivity::class.java)
        startActivity(intent)
    }
}