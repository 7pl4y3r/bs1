package com.apps.a7pl4y3r.yourweek.independent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apps.a7pl4y3r.yourweek.R

class About : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_about)
        setActionBar()
    }

    private fun setActionBar() {
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
