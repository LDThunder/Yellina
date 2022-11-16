package com.eastail.yellina

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.eastail.yellina.Database.DbHelper
import com.eastail.yellina.databinding.FragmentFgStoreBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import pl.droidsonroids.gif.GifImageView

class FgStore : Fragment(R.layout.fragment_fg_store) {
    private lateinit var bind: FragmentFgStoreBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabStore: TabLayout
    private lateinit var dbHelper: DbHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentFgStoreBinding.bind(view)
        viewPager = view.findViewById(R.id.pagerStore)
        tabStore = view.findViewById(R.id.tabStore)
        dbHelper = DbHelper(context)
        initViewPager()
        requireActivity().findViewById<GifImageView>(R.id.shrekGif).visibility = View.INVISIBLE
    }


    private fun initViewPager() {
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        FgStoreWeapon.newInstance()
                    }
                    else -> {
                        FgStoreSpecial.newInstance()
                    }
                }
            }

            override fun getItemCount(): Int {
                return 2
            }
        }

        TabLayoutMediator(tabStore, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Оружие"
                else -> "Эффекты"
            }
        }.attach()
    }
}