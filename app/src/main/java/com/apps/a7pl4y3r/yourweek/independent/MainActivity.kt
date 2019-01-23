package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.Gravity
import android.view.MenuItem
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.dayfragments.*
import com.apps.a7pl4y3r.yourweek.helpers.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var dayID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_main)
        setDrawer()

        val setViewPager = SetViewPager(supportFragmentManager, this)
        setViewPager.execute()
    }

    override fun onResume() {

        val sharedPreferences = getSharedPreferences(SettChangedTheme, Context.MODE_PRIVATE)
        if(sharedPreferences.getBoolean(valueSettChangedTheme, false)) {

            val editor = sharedPreferences.edit()
            editor.putBoolean(valueSettChangedTheme, false)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        if(getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE).getBoolean(valueSettTaskWasAdded, false) && dayID != -1) {

            setViewPagerAdapter()
            viewPager.currentItem = dayID

            getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE).edit().putBoolean(valueSettTaskWasAdded, false).apply()
        }
        super.onResume()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.nav_settings -> startActivity(Intent(this, Settings::class.java))
            R.id.nav_add -> {
                dayID = viewPager.currentItem
                startActivity(Intent(this, Add::class.java).putExtra("DAY", dayID))
            }
            R.id.nav_edit_tasks -> {
                dayID = viewPager.currentItem
                startActivity(Intent(this, UpdateTask::class.java).putExtra("DAYID", dayID))
            }

        }

        drawer_layout.closeDrawer(Gravity.START)
        return true
    }

    private fun setDrawer() {

        setSupportActionBar(mainToolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, mainToolbar, R.string.openDrawer, R.string.closeDrawer)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val themeId = getSharedPreferences(settTheme, Context.MODE_PRIVATE).getInt(valueSettTheme, 1)
        if (themeId == 2 || themeId == 5) {

            val menu = nav_view.menu
            val tools = menu.findItem(R.id.nav_title)
            val spanStr = SpannableString(tools.title)
            spanStr.setSpan(TextAppearanceSpan(this, R.style.DrawerTextDark), 0, spanStr.length, 0)
            tools.title = spanStr

            nav_view.itemTextColor = ContextCompat.getColorStateList(this, android.R.color.white)
            nav_view.itemIconTintList = ContextCompat.getColorStateList(this, android.R.color.white)
        }

        nav_view.setNavigationItemSelectedListener(this)
    }

    private class SetViewPager(
        private val fm: FragmentManager, private val activity: MainActivity) : AsyncTask<Int, Int, String>() {

        private val weakReference: WeakReference<MainActivity> = WeakReference(activity)
        private lateinit var viewPagerAdapter: ViewPagerAdapter
        private val is7Days = (activity as Context).getSharedPreferences(settNumOfDays, Context.MODE_PRIVATE).getBoolean(valueSettNumOfDays, false)

        override fun doInBackground(vararg params: Int?): String {

            viewPagerAdapter = ViewPagerAdapter(fm)
            viewPagerAdapter.addFrag(Monday(), "Monday")
            viewPagerAdapter.addFrag(Tuesday(), "Tuesday")
            viewPagerAdapter.addFrag(Wednesday(), "Wednesday")
            viewPagerAdapter.addFrag(Thursday(), "Thursday")
            viewPagerAdapter.addFrag(Friday(), "Friday")

            if(is7Days) {
                viewPagerAdapter.addFrag(Saturday(), "Saturday")
                viewPagerAdapter.addFrag(Sunday(), "Sunday")
            }

            return "Done"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            val mActivity = weakReference.get()
            if (mActivity == null || mActivity.isFinishing)
                return

            mActivity.viewPager.adapter = viewPagerAdapter
            if (is7Days) {
                activity.viewPager.currentItem = activity.getNORMALDayId()
                activity.viewPager.offscreenPageLimit = 7

            } else {

                val currentDay = activity.getNORMALDayId()
                activity.viewPager.currentItem = if (currentDay < 5) currentDay else 0
                activity.viewPager.offscreenPageLimit = 5
            }
        }
    }

    //Sets the ViewPager Adapter (The fragments for each day are being added here)
    private fun setViewPagerAdapter() {

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(Monday(), "Monday")
        adapter.addFrag(Tuesday(), "Tuesday")
        adapter.addFrag(Wednesday(), "Wednesday")
        adapter.addFrag(Thursday(), "Thursday")
        adapter.addFrag(Friday(), "Friday")

		//true if the week is 7 days long | false if the week is 5 days long
        if(getSharedPreferences(settNumOfDays, Context.MODE_PRIVATE)
                .getBoolean(valueSettNumOfDays, false)) {

            adapter.addFrag(Saturday(), "Saturday")
            adapter.addFrag(Sunday(), "Sunday")

            viewPager.adapter = adapter
            viewPager.currentItem = getNORMALDayId()
            viewPager.offscreenPageLimit = 7

        } else {

            val currentDay = getNORMALDayId()
            viewPager.adapter = adapter
            viewPager.currentItem = if(currentDay < 5) currentDay else 0
            viewPager.offscreenPageLimit = 5
        }
    }

    private fun getNORMALDayId(): Int {

        when(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {

            Calendar.MONDAY -> return 0
            Calendar.TUESDAY -> return 1
            Calendar.WEDNESDAY -> return 2
            Calendar.THURSDAY -> return 3
            Calendar.FRIDAY -> return 4
            Calendar.SATURDAY -> return 5
            Calendar.SUNDAY -> return 6
        }
        return -1
    }
}