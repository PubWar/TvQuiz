package com.pubwar.quiz

import android.app.Activity

object ActivityProvider {
    private var currentActivity: Activity? = null

    fun setActivity(activity: Activity?) {
        currentActivity = activity
    }

    fun getActivity(): Activity? = currentActivity
}