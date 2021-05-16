package com.konovalovea.expsampling.screens.record

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.screens.main.MainActivity
import com.konovalovea.expsampling.screens.record.model.RecordScreenState
import com.konovalovea.expsampling.screens.record.recycler.optionrecycler.OptionsAdapter
import com.konovalovea.expsampling.screens.record.recycler.optionrecycler.OptionsLayoutManager
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.view.*
import kotlinx.android.synthetic.main.inc_error.view.*

    class RecordFragment : Fragment() {

    private val viewModel: RecordViewModel by activityViewModels()

    private lateinit var optionsAdapter: OptionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.isTutorial = it.getBoolean(IS_TUTORIAL_KEY)
        }
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
        when(recordScreenState) {
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

        fun newInstance(isTutorial: Boolean) =
            RecordFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_TUTORIAL_KEY, isTutorial)
                }
            }
    }
}