package com.konovalovea.expsampling.screens.record.recycler.radiorecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.screens.record.model.options.RadioOption
import kotlinx.android.synthetic.main.vh_radio_item.view.*

class VerticalRadioGroupAdapter(
    private var radioItems: List<RadioOption> = emptyList()
) : RecyclerView.Adapter<VerticalRadioGroupAdapter.ViewHolder>() {

    var currentlySelected: Int? = null

    var onSelectListener: (layoutPosition: Int, isChecked: Boolean) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vh_radio_item, parent, false)
        return ViewHolder(
            view,
            this::onItemCheckedListener
        )
    }

    override fun getItemCount(): Int {
        return radioItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isSelected = currentlySelected != null && position == currentlySelected
        holder.bind(radioItems[position], isSelected)
    }

    private fun onItemCheckedListener(layoutPosition: Int) {
        val isChecked = currentlySelected != layoutPosition
        onSelectListener(layoutPosition, isChecked)

        if (isChecked) {
            currentlySelected = layoutPosition
            notifyDataSetChanged()
        } else {
            currentlySelected = null
        }
    }

    fun setItems(radioItems: List<RadioOption>) {
        this.radioItems = radioItems
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val view: View,
        private val onSelectListener: (layoutPosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(radioOption: RadioOption, isSelected: Boolean) {
            view.label.text = radioOption.label

            view.toggleImage.setImageResource(
                if (isSelected) {
                    CHECKED_DRAWABLE
                } else {
                    UNCHECKED_DRAWABLE
                }
            )

            view.radioItem.setOnClickListener {
                onSelectListener(layoutPosition)
            }
        }

        private companion object {
            const val UNCHECKED_DRAWABLE = R.drawable.button_radio_stroke
            const val CHECKED_DRAWABLE = R.drawable.button_radio_pressed
        }
    }
}