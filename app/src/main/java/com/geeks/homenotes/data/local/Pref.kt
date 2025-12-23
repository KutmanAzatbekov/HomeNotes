package com.geeks.homenotes.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.geeks.homenotes.core.AppKey

class Pref(context: Context) {

    val pref : SharedPreferences = context.getSharedPreferences(AppKey.KEY_PREF, Context.MODE_PRIVATE)

    fun setOnBoardingShown() {
        pref.edit {putBoolean(AppKey.KEY, true)}
    }

    fun isOnBoardingShown(): Boolean {
        return pref.getBoolean(AppKey.KEY, false)
    }

}