package com.example.bottomnavsampleapp

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import com.google.android.material.navigation.NavigationBarView
import com.dicoding.asclepius.R

fun NavigationBarView.startActivityWithNavBarSharedTransition(
    activity:Activity,
    intent: Intent
) {
    val options = ActivityOptions.makeSceneTransitionAnimation(
        activity,
        this,
        activity.getString(R.string.nav_view_shared_element_transition_name)
    ).toBundle()
    activity.startActivity(intent, options)
}