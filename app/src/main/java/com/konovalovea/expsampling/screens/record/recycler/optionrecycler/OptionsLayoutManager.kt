package com.konovalovea.expsampling.screens.record.recycler.optionrecycler

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class OptionsLayoutManager(context: Context) : LinearLayoutManager(context) {

    override fun canScrollVertically(): Boolean {
        return if (itemCount == 1) {
            false
        } else {
            super.canScrollVertically()
        }
    }
}