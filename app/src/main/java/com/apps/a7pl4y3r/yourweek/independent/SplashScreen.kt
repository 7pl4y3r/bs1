package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apps.a7pl4y3r.yourweek.R
import java.util.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_splash_screen)

        val intent = Intent(this, MainActivity::class.java)
        val timer = object : Thread() {
            override fun run() {

                try {
                    setSwitchTheme()
                    Thread.sleep(800)

                } catch (e: InterruptedException) {
                    e.printStackTrace()

                } finally {
                    startActivity(intent)
                    finish()
                }
            }
        }; timer.start()
    }

    private fun setSwitchTheme() {

        if(getSharedPreferences(setSwitchTheme, Context.MODE_PRIVATE).getBoolean(valSetSwitchTheme, false)) {

            val now = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val time1 = getSharedPreferences(setSwitchTime1, Context.MODE_PRIVATE).getInt(valSetSwitchTime1, 8)
            val time2 = getSharedPreferences(setSwitchTime2, Context.MODE_PRIVATE).getInt(valSetSwitchTime2, 20)

            if(now in time1 until time2) {

                val t1 = getSharedPreferences(setSwitchTheme1, Context.MODE_PRIVATE).getInt(valSetSwitchTheme1, 1)
                getSharedPreferences(settTheme, Context.MODE_PRIVATE).edit().putInt(valueSettTheme, t1).apply()

            } else {

                val t2 = getSharedPreferences(setSwitchTheme2, Context.MODE_PRIVATE).getInt(valSetSwitchTheme2, 2)
                getSharedPreferences(settTheme, Context.MODE_PRIVATE).edit().putInt(valueSettTheme, t2).apply()
            }
        }
    }
}
