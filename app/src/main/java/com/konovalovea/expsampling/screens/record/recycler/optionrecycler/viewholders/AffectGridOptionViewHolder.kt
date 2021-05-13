package com.konovalovea.expsampling.screens.record.recycler.optionrecycler.viewholders

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.screens.record.model.options.AffectGridOption
import com.konovalovea.expsampling.view.AffectGrid
import kotlinx.android.synthetic.main.vh_affect_grid.view.*

class AffectGridOptionViewHolder(private val view: View): RecyclerView.ViewHolder(view)  {

    fun bind(affectGridOption: AffectGridOption) {
        view.affectGrid.xRelativeValue = affectGridOption.valueX
        view.affectGrid.yRelativeValue = affectGridOption.valueY
        view.affectGrid.cellCount = affectGridOption.delimiterCount
        view.affectGrid.setOnTouchListener { view, event ->
            val affectGrid = view as AffectGrid
            if (event.action == MotionEvent.ACTION_UP) {
                affectGridOption.valueX = affectGrid.xRelativeValue
                affectGridOption.valueY = affectGrid.yRelativeValue
            }
            affectGrid.performClick()
            false
        }
    }

    companion object {
        private const val layoutResource = R.layout.vh_affect_grid

        fun from(parent: ViewGroup): AffectGridOptionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutResource, parent, false)
            return AffectGridOptionViewHolder(
                view
            )
        }
    }
}