package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.widget.Toast
import com.apps.a7pl4y3r.yourweek.R


fun setAppTheme(context: Context) {

    when(context.getSharedPreferences(settTheme, Context.MODE_PRIVATE).getInt(valueSettTheme, 1)) {
            //SkyBlue
            1 -> context.setTheme(R.style.AppTheme)
            //OceanDarkBlueTheme
            2 -> context.setTheme(R.style.OceanDarkBlueTheme)
            //GrassGreenTheme
            3 -> context.setTheme(R.style.GrassGreenTheme)
            //MarsRedTheme
            4 -> context.setTheme(R.style.MarsRedTheme)
            //AmoledDarkTheme
            5 -> context.setTheme(R.style.AMOLEDDark)
    }
}

fun toastMessage(context: Context, message: String, isLong: Boolean) {
    Toast.makeText(context, message,
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}
