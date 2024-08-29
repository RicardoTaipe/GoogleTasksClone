package com.example.googletasksclone.customviews

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout

class CustomTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TabLayout(context, attrs) {

    private var lastSelectedTabPosition = -1
    private var onCustomTabSelected: (() -> Unit)? = null

    fun setOnCustomTabSelectedListener(listener: () -> Unit) {
        this.onCustomTabSelected = listener
    }

    override fun selectTab(tab: Tab?, updateIndicator: Boolean) {
        if (tab != null && tab.position == tabCount - 1) {
            // If it's the last tab, do not update the indicator
            setScrollPosition(lastSelectedTabPosition, 0f, true, false)
            onCustomTabSelected?.invoke()
        } else {
            // save last selected tab and update the indicator
            lastSelectedTabPosition = tab?.position ?: lastSelectedTabPosition
            super.selectTab(tab, updateIndicator)
        }
    }

    fun addCustomTab(text: String) {
        addTab(newTab().apply {
            this.text = text
        })
    }
}
