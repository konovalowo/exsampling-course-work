package com.konovalovea.expsampling.screens.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.konovalovea.expsampling.R

class HelpBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help_bottom_sheet, container, false)
    }

    companion object {

        fun newInstance(bundle: Bundle): HelpBottomSheet {
            val fragment = HelpBottomSheet()
            fragment.arguments = bundle
            return fragment
        }
    }
}