package com.konovalovea.expsampling.screens.record

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.app.appComponent
import com.konovalovea.expsampling.model.PreferenceStats
import com.konovalovea.expsampling.repository.PreferenceService
import com.konovalovea.expsampling.screens.main.MainActivity
import com.konovalovea.expsampling.screens.record.model.RecordScreenState
import com.konovalovea.expsampling.screens.record.recycler.optionrecycler.OptionsAdapter
import com.konovalovea.expsampling.screens.record.recycler.optionrecycler.OptionsLayoutManager
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.view.*
import kotlinx.android.synthetic.main.inc_error.view.*
import javax.inject.Inject
import kotlin.math.max

class RecordFragment() : Fragment() {

    @Inject
    lateinit var viewModel: RecordViewModel

    @Inject
    lateinit var preferenceService: PreferenceService

    private lateinit var optionsAdapter: OptionsAdapter

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.isTutorial = it.getBoolean(IS_TUTORIAL_KEY)
            viewModel.notificationId = it.getInt(NOTIFICATION_ID_KEY)
        }
        if (!viewModel.isTutorial)
            (context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .cancelAll()
        val stats = preferenceService.getStats()
        preferenceService.saveStats(
            PreferenceStats(stats.recordsMade, max(stats.lastRecordId, viewModel.notificationId))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_record, container, false)

        optionsAdapter = OptionsAdapter()

        view.recyclerView.apply {
            layoutManager = OptionsLayoutManager(this.context)
            adapter = optionsAdapter
        }

        view.nextButton.setOnClickListener { viewModel.onNext() }
        view.backButton.setOnClickListener { viewModel.onPrevious() }

        view.errorLayout.retryButton.setOnClickListener { viewModel.loadRecord() }

        setUpObservers()

        viewModel.loadRecord()

        return view
    }

    private fun setUpObservers() {
        viewModel.recordScreenState.observe(viewLifecycleOwner, Observer(this::renderState))
        viewModel.finishEvent.observe(viewLifecycleOwner, Observer(this::onFinishEvent))
    }

    private fun renderState(recordScreenState: RecordScreenState) {
        when (recordScreenState) {
            is RecordScreenState.Loading -> onStateLoading()
            is RecordScreenState.Loaded -> onStateLoaded(recordScreenState)
            is RecordScreenState.Error -> onStateError()
        }
    }

    private fun onFinishEvent(finish: Boolean?) {
        if (finish == null)
            return
        if (finish) {
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        } else {
            Toast.makeText(context, "Не удалось отправить ответ", Toast.LENGTH_LONG).show()
        }
    }

    private fun onStateLoading() {
        header.text = getString(R.string.loading)
        progressBar.isVisible = true
        questionLayout.isVisible = false
        errorLayout.isVisible = false

        nextButton.isEnabled = false
        backButton.isEnabled = false
    }

    private fun onStateLoaded(recordScreenStateLoaded: RecordScreenState.Loaded) {
        header.text = recordScreenStateLoaded.question.header
        progressBar.isVisible = false
        errorLayout.isVisible = false
        questionLayout.isVisible = true
        questionText.text = recordScreenStateLoaded.question.text
        if (recordScreenStateLoaded.question.subtext != null) {
            questionSubText.text = recordScreenStateLoaded.question.subtext
            questionSubText.isVisible = true
        } else {
            questionSubText.isVisible = false
        }
        optionsAdapter.setItems(recordScreenStateLoaded.question.options)

        nextButton.isEnabled = true
        if (recordScreenStateLoaded.isNextAvailable) {
            nextButton.setText(R.string.next)
            nextButton.setOnClickListener { viewModel.onNext() }
        } else {
            nextButton.setText(R.string.finish)
            nextButton.setOnClickListener { viewModel.onFinish() }
        }
        backButton.isEnabled = recordScreenStateLoaded.isPreviousAvailable
    }

    private fun onStateError() {
        header.text = getString(R.string.error)
        progressBar.isVisible = false
        questionLayout.isVisible = false
        errorLayout.isVisible = true
    }

    companion object {

        private const val IS_TUTORIAL_KEY = "is_tutorial"
        private const val NOTIFICATION_ID_KEY = "notification_id"

        fun newInstance(isTutorial: Boolean, notificationId: Int) =
            RecordFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_TUTORIAL_KEY, isTutorial)
                    putInt(NOTIFICATION_ID_KEY, notificationId)
                }
            }
    }
}