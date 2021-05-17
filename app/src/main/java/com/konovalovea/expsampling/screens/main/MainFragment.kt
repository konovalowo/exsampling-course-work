package com.konovalovea.expsampling.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.screens.main.model.MainScreenState
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.inc_contacts.view.*
import kotlinx.android.synthetic.main.inc_error.view.*
import kotlinx.android.synthetic.main.inc_notification.view.*
import kotlinx.android.synthetic.main.inc_record_stats.view.*
import kotlinx.android.synthetic.main.inc_research_stats.view.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        view.tutorialButton.setOnClickListener {
            viewModel.onTutorialButtonClick()
        }

        view.mainErrorLayout.retryButton.setOnClickListener {
            viewModel.loadDashboard()
        }

        setUpObservers()

        viewModel.loadDashboard()

        return view
    }

    private fun setUpObservers() {
        viewModel.mainScreenState.observe(viewLifecycleOwner, Observer(this::renderState))
        viewModel.startActivityEvent.observe(viewLifecycleOwner, Observer { intentConsumable ->
            intentConsumable?.handle { startActivity(it) }
            activity?.finish()
        })
    }

    private fun renderState(mainScreenState: MainScreenState) {
        when (mainScreenState) {
            is MainScreenState.Loading -> {
                progressBar.isVisible = true
                dashboardLayout.isVisible = false
                mainErrorLayout.isVisible = false
            }
            is MainScreenState.Loaded -> {
                progressBar.isVisible = false
                dashboardLayout.isVisible = true
                mainErrorLayout.isVisible = false

                header.text = mainScreenState.dashboard.day

                recordStatsLayout.apply {
                    mainScreenState.dashboard.statsGroup.apply {
                        recordsMadeText.text = recordMade
                        recordsMadeLabel.text = resources.getQuantityString(
                            R.plurals.records_made,
                            recordMade.toInt()
                        )

                        recordsSkippedText.text = recordsSkipped
                        recordsSkippedLabel.text = resources.getQuantityString(
                            R.plurals.skipped,
                            recordsSkipped.toInt()
                        )

                        recordsToGoText.text = recordsToGo
                        recordsToGoLabel.text = resources.getQuantityString(
                            R.plurals.togo,
                            recordsToGo.toInt()
                        )
                    }
                }

                researchStatsLayout.apply {
                    mainScreenState.dashboard.statsGroup.apply {
                        daysToGoText.text = daysToGo
                        daysToGoLabel.text = resources.getQuantityString(
                            R.plurals.days_till_the_end_of_research,
                            daysToGo.toInt()
                        )
                    }
                }

                notificationLayout.apply {
                    mainScreenState.dashboard.infoGroup.apply {
                        notificationTimeStartText.text = notificationStartTime
                        notificationTimeEndText.text = notificationEndTime
                    }
                }

                contactsLayout.apply {
                    mainScreenState.dashboard.contactsGroup.apply {
                        contactsNameText.text = contactName
                        contactsEmailText.text = email
                        contactsPhoneText.text = phone
                    }
                }
            }
            is MainScreenState.Error -> {
                progressBar.isVisible = false
                dashboardLayout.isVisible = false
                mainErrorLayout.isVisible = true
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}