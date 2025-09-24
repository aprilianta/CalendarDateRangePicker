package com.aprilianta

import android.view.View
import android.view.ViewGroup


fun ViewGroup.forEachDescendant(action: (View) -> Unit) {
    fun dfs(v: View) {
        action(v)
        if (v is ViewGroup) for (i in 0 until v.childCount) dfs(v.getChildAt(i))
    }
    dfs(this)
}