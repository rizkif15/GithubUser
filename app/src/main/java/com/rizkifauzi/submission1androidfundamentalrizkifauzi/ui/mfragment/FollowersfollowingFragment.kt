package com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.mfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.R
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc.DetailViewModel
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc.UsersAdapter

class FollowersfollowingFragment : Fragment() {

    private var position: Int = 1
    private var username: String? = null
    private lateinit var progressBar3: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION, 1)
            username = it.getString(ARG_USERNAME)
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsersAdapter // Sesuaikan dengan nama adapter Anda

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar3 = view.findViewById(R.id.progressBar3)
        progressBar3.visibility = View.VISIBLE

        // Inisialisasi RecyclerView dan adapter
        recyclerView = view.findViewById(R.id.rvFollow)
        adapter = UsersAdapter(requireActivity()) // Gantilah dengan nama adapter Anda

        // Set layout manager dan adapter ke RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observasi data followers atau following dari ViewModel
        val viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        val position = arguments?.getInt(ARG_POSITION, 1)
        val username = arguments?.getString(ARG_USERNAME)

        if (position == 1) {
            viewModel.getFollowers(username ?: "").observe(viewLifecycleOwner) { followers ->
                // Update data di adapter ketika data followers berubah
                adapter.submitList(followers)
                progressBar3.visibility = View.GONE
            }
        } else {
            viewModel.getFollowing(username ?: "").observe(viewLifecycleOwner) { following ->
                // Update data di adapter ketika data following berubah
                adapter.submitList(following)
                progressBar3.visibility = View.GONE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followersfollowing, container, false)
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}
