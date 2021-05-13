package com.konovalovea.expsampling.screens.record.recycler.optionrecycler.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.screens.record.model.options.DiscreteSliderOption
import kotlinx.android.synthetic.main.vh_discrete_slider_w_counter.view.*
import kotlin.math.abs

class DiscreteSliderOptionViewHolder(private val view: View): RecyclerView.ViewHolder(view)  {

    fun bind(discreteSliderOption: DiscreteSliderOption) {
        view.seekBar.progress = discreteSliderOption.value
        view.seekBar.max = abs(discreteSliderOption.max - discreteSliderOption.min)
        view.counter.text = (discreteSliderOption.value + discreteSliderOption.min).toString()
        view.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                view.counter.text = (progress + discreteSliderOption.min).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.progress?.let {
                    discreteSliderOption.value = it
                }
            }
        })
        view.label.text = discreteSliderOption.label
    }

    companion object {
        private const val layoutResource = R.layout.vh_discrete_slider_w_counter

        fun from(parent: ViewGroup): DiscreteSliderOptionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutResource, parent, false)
            return DiscreteSliderOptionViewHolder(
                view
            )
        }
    }
}