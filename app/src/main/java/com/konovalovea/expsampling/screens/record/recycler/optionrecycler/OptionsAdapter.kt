package com.konovalovea.expsampling.screens.record.recycler.optionrecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konovalovea.expsampling.screens.record.model.options.*
import com.konovalovea.expsampling.screens.record.recycler.optionrecycler.viewholders.*

const val DISCRETE_SLIDER_OPTION_VIEW_TYPE = 0
const val SLIDER_OPTION_VIEW_TYPE = 2
const val RADIO_GROUP_OPTION_VIEW_TYPE = 3
const val AFFECT_GRID_OPTION_VIEW_TYPE = 4
const val VERTICAL_RADIO_GROUP_VIEW_TYPE = 5

class OptionsAdapter(
    private var options: List<ListOption> = emptyList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        return when (options[position]) {
            is DiscreteSliderOption -> DISCRETE_SLIDER_OPTION_VIEW_TYPE
            is AffectGridOption -> AFFECT_GRID_OPTION_VIEW_TYPE
            is RadioGroupOption -> RADIO_GROUP_OPTION_VIEW_TYPE
            is SliderOption -> SLIDER_OPTION_VIEW_TYPE
            is VerticalRadioGroupOption -> VERTICAL_RADIO_GROUP_VIEW_TYPE
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RADIO_GROUP_OPTION_VIEW_TYPE -> RadioGroupOptionViewHolder.from(parent)
            DISCRETE_SLIDER_OPTION_VIEW_TYPE -> DiscreteSliderOptionViewHolder.from(parent)
            SLIDER_OPTION_VIEW_TYPE -> SliderOptionViewHolder.from(parent)
            AFFECT_GRID_OPTION_VIEW_TYPE -> AffectGridOptionViewHolder.from(parent)
            VERTICAL_RADIO_GROUP_VIEW_TYPE -> VerticalRadioGroupOptionViewHolder.from(parent)
            else -> RadioGroupOptionViewHolder.from(parent)
        }
    }

    override fun getItemCount() = options.count()

    fun setItems(options: List<ListOption>) {
        this.options = options
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RadioGroupOptionViewHolder -> holder.bind(
                options[position] as RadioGroupOption
            )

            is DiscreteSliderOptionViewHolder -> holder.bind(
                options[position] as DiscreteSliderOption
            )

            is SliderOptionViewHolder -> holder.bind(
                options[position] as SliderOption
            )

            is AffectGridOptionViewHolder -> holder.bind(
                options[position] as AffectGridOption
            )

            is VerticalRadioGroupOptionViewHolder -> holder.bind(
                options[position] as VerticalRadioGroupOption
            )
        }
    }
}