package com.konovalovea.expsampling.screens.record.recycler.optionrecycler.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.screens.record.model.options.SliderOption
import kotlinx.android.synthetic.main.vh_slider.view.*
import kotlin.math.abs

class SliderOptionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(sliderOption: SliderOption) {
        view.textLeftSide.text = sliderOption.labelLeft
        view.textRightSide.text = sliderOption.labelRight
        if (sliderOption.max != null && sliderOption.min != null)
            view.seekBar.max = abs(sliderOption.max - sliderOption.min)
        view.seekBar.progress = sliderOption.value
        view.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) =
                Unit

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.progress?.let {
                    sliderOption.value = it
                }
            }
        })
    }

    companion object {
        private const val layoutResource = R.layout.vh_slider

        fun from(parent: ViewGroup): SliderOptionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutResource, parent, false)
            return SliderOptionViewHolder(
                view
            )
        }
    }
}