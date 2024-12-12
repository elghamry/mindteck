package com.test.mindteck.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.mindteck.fragment.ViewPagerFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val images: List<Int>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return images.size
    }

    override fun createFragment(position: Int): Fragment {
        return ViewPagerFragment.newInstance(images[position])
    }
}