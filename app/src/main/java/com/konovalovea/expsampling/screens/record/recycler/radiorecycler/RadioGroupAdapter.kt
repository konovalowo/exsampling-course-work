package com.konovalovea.expsampling.screens.record.recycler.radiorecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.konovalovea.expsampling.R

class RadioGroupAdapter() : RecyclerView.Adapter<RadioGroupAdapter.ViewHolder>() {

    var currentlySelected: Int? = null

    var onSelectListener: (layoutPosition: Int, isChecked: Boolean) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_toggle_number, parent, false)
        return ViewHolder(
            view,
            this::onItemCheckedListener
        )
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, position == currentlySelected)
    }

    private fun onItemCheckedListener(layoutPosition: Int, isChecked: Boolean) {
        onSelectListener(layoutPosition, isChecked)

        if (isChecked) {
            currentlySelected = layoutPosition
            notifyDataSetChanged()
        } else {
            currentlySelected = null
        }
    }

    class ViewHolder(
        private val view: View,
        private val onSelectListener: (layoutPosition: Int, isChecked: Boolean) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private lateinit var toggleButton: ToggleButton

        fun bind(position: Int, isSelected: Boolean) {
            toggleButton = view.findViewById(R.id.toggleButton)
            val buttonLabel = (position + 1).toString()

            toggleButton.textOff = buttonLabel
            toggleButton.textOn = buttonLabel

            toggleButton.isChecked = isSelected

            toggleButton.setOnClickListener {
                onSelectListener(layoutPosition, toggleButton.isChecked)
            }
        }
    }
}