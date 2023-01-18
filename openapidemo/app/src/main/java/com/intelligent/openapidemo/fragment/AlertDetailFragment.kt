package com.intelligent.openapidemo.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.intelligent.openapidemo.R
import com.intelligent.openapidemo.R.layout
import com.intelligent.openapidemo.viewmodels.AlertDetailViewModel
import com.sca.in_telligent.openapi.data.network.model.NotificationLanguage
import java.text.SimpleDateFormat
import java.util.*


class AlertDetailFragment : Fragment() {

    private var languageOption: List<NotificationLanguage> = ArrayList()
    companion object {

        private const val ARG_PARAM_NOTIFICATION_ID = "notificationId"
        fun newInstance(notificationId: Int): AlertDetailFragment {
            val bundle = Bundle()
            bundle.putInt(ARG_PARAM_NOTIFICATION_ID, notificationId)
            val alertDetailFragment = AlertDetailFragment()
            alertDetailFragment.arguments = bundle
            return alertDetailFragment

        }
    }
    private var notificationId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            notificationId = it.getInt(ARG_PARAM_NOTIFICATION_ID)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(layout.fragment_alert_detail, container, false)
        val titleTextView: TextView = rootView.findViewById(R.id.alert_title)
        val descriptionTextView: TextView = rootView.findViewById(R.id.alert_description)
        val dateTextView: TextView = rootView.findViewById(R.id.alert_date)
        val alertTypeTextView: TextView = rootView.findViewById(R.id.alert_type)
        val translationButton: Button = rootView.findViewById(R.id.translate_button)

        translationButton.setOnClickListener {

            displayLanguages()

        }
        val alertDetailViewModel =
            ViewModelProvider(this)[AlertDetailViewModel::class.java]
        activity?.let { alertDetailViewModel.getLanguages() }
        activity?.let { alertDetailViewModel.getAlertDetail(notificationId) }
        alertDetailViewModel.alertDetail.observe(viewLifecycleOwner) { alertDetail ->

            val date = alertDetail.date
            val alertDate = SimpleDateFormat("MM/dd/yyyy").format(Date(date))
            titleTextView.text = alertDetail.title
            descriptionTextView.text = alertDetail.description
            dateTextView.text = alertDate
            alertTypeTextView.text = alertDetail.type

        }
        alertDetailViewModel.notificationLanguages.observe(viewLifecycleOwner) { notificationLanguages ->

            languageOption = notificationLanguages.languages

        }
        alertDetailViewModel.notificationTitle.observe(viewLifecycleOwner) { title ->

            titleTextView.text = title.toString()

        }
        alertDetailViewModel.description.observe(viewLifecycleOwner) { description ->

            descriptionTextView.text = description

        }
        return rootView
    }
    private fun displayLanguages() {

        val alertDetailViewModel =
            ViewModelProvider(this)[AlertDetailViewModel::class.java]
        val builder = MaterialAlertDialogBuilder(requireActivity())
        builder
            .setTitle("select a language")
            .setItems(
                languageOption.map { it.name }.toTypedArray()

            ) { _: DialogInterface?, pos: Int ->
                alertDetailViewModel.getTranslation(
                    notificationId.toString(),
                    languageOption[pos].language
                )
            }
        val dialog = builder.create()
        dialog.show()
    }
}

