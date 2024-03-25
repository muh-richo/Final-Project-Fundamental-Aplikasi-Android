package com.example.githubuserapp.ui.detailuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    private lateinit var binding : FragmentFollowBinding
    private lateinit var viewModel: FollowViewModel

    private var position: Int = 0
    private var username: String? = ""

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "curr_username"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)

        showRecyclerList()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            viewModel.listFollowers.observe(viewLifecycleOwner) {
                setFollowerData(it)
            }
            viewModel.getFollowers(username!!)
        } else {
            viewModel.listFollowing.observe(viewLifecycleOwner) {
                setFollowerData(it)
            }
            viewModel.getFollowing(username!!)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showRecyclerList() {
        val layoutInflater = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutInflater
    }

    private fun setFollowerData(user: List<ItemsItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(user)
        binding.recyclerView.adapter = adapter
    }
}