package com.example.mowtv.adapter

import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter : FragmentPagerAdapter {

    private var fragmentList: ArrayList<Fragment> = ArrayList()
    private var fragmentTitleList: ArrayList<String> = ArrayList()

    constructor(supportFragmentManager: FragmentManager)
            : super(supportFragmentManager)

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}