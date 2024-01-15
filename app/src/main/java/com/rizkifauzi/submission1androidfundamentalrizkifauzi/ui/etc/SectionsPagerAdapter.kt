package com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.mfragment.FollowersfollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String? = null
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowersfollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowersfollowingFragment.ARG_POSITION, position + 1)
            putString(FollowersfollowingFragment.ARG_USERNAME, username)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}