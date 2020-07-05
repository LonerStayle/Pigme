package com.example.wisesaying.view.visibility

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.example.wisesaying.preference.PrefVisibility

fun visibilityGoneModeUse(frameLayoutSelfstotyUsagemarks: FrameLayout,context: Context) {
    frameLayoutSelfstotyUsagemarks.visibility = View.GONE
    PrefVisibility.getInstance(context).fremeLayoutSelfstoryUsagemarksVisibility =
        0x00000008
}