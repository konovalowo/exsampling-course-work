package com.konovalovea.expsampling.screens.record.recycler.optionrecycler.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.screens.record.model.options.RadioGroupOption
import com.konovalovea.expsampling.screens.record.recycler.radiorecycler.RadioGroupAdapter
import kotlinx.android.synthetic.main.vh_radio_group.view.*

class RadioGroupOptionViewHolder private constructor(
    private var view: View
) : RecyclerView.ViewHolder(view) {

    private val radioAdapter: RadioGroupAdapter = RadioGroupAdapter()

    fun bind(radioGroupOption: RadioGroupOption) {
        view.title.text = radioGroupOption.label
        view.radioGroupRecycler.adapter = radioAdapter
        radioAdapter.currentlySelected = radioGroupOption.value
        radioAdapter.onSelectListener = { position, isChecked ->
            radioGroupOption.value = if (isChecked) position else null
        }
    }

    companion object {
        private const val layoutResource = R.layout.vh_radio_group

        fun from(parent: ViewGroup): RadioGroupOptionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutResource, parent, false)
            return RadioGroupOptionViewHolder(view)
        }
    }
}