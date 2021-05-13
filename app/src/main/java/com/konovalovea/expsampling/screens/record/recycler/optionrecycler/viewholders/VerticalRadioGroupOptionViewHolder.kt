package com.konovalovea.expsampling.screens.record.recycler.optionrecycler.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.screens.record.model.options.VerticalRadioGroupOption
import com.konovalovea.expsampling.screens.record.recycler.radiorecycler.VerticalRadioGroupAdapter
import kotlinx.android.synthetic.main.vh_vertical_radio_group.view.*

class VerticalRadioGroupOptionViewHolder private constructor(
    private var view: View
) : RecyclerView.ViewHolder(view)  {

    private val verticalRadioAdapter: VerticalRadioGroupAdapter = VerticalRadioGroupAdapter()

    fun bind(verticalRadioGroupOption: VerticalRadioGroupOption) {
        view.verticalRadioGroupRecycler.adapter = verticalRadioAdapter
        verticalRadioAdapter.setItems(verticalRadioGroupOption.radioOptions)
        verticalRadioAdapter.currentlySelected = verticalRadioGroupOption.value
        verticalRadioAdapter.onSelectListener = { position, isChecked ->
            verticalRadioGroupOption.value = if (isChecked) position else null
        }
    }

    companion object {
        private const val layoutResource = R.layout.vh_vertical_radio_group

        fun from(parent: ViewGroup): VerticalRadioGroupOptionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutResource, parent, false)
            return VerticalRadioGroupOptionViewHolder(view)
        }
    }
}