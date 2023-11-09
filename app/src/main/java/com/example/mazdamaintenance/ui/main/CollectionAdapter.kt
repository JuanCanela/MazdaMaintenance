package com.example.tablayoutkotlin.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mazdamaintenance.ui.main.EmailFragment

class CollectionAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SmsFragment()
            1 -> CallFragment()
            2 -> EmailFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}