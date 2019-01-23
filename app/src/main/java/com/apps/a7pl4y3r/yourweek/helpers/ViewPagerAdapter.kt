package com.apps.a7pl4y3r.yourweek.helpers

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList

class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val fragmentList = ArrayList<Fragment>()
    private val fragmentListTitles = ArrayList<String>()

    override fun getItem(position: Int): Fragment = fragmentList[position]
    override fun getCount(): Int = fragmentList.size
    override fun getPageTitle(position: Int): CharSequence? = fragmentListTitles[position]

    fun addFrag(fragment: Fragment, title:String) {
        fragmentList.add(fragment)
        fragmentListTitles.add(title)
    }
}