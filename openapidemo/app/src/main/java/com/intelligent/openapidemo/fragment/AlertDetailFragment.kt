package com.intelligent.openapidemo.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.intelligent.openapidemo.R
import com.intelligent.openapidemo.R.layout
import com.intelligent.openapidemo.viewmodels.AlertDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class AlertDetailFragment : Fragment() {
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
        val alertDetailViewModel =
            ViewModelProvider(this).get(AlertDetailViewModel::class.java)
        activity?.let { alertDetailViewModel.getAlertDetail(notificationId) }
        alertDetailViewModel.alertDetail.observe(viewLifecycleOwner) { alertDetail ->

            val date = alertDetail.date
            val alertDate = SimpleDateFormat("MM/dd/yyyy").format(Date(date))


            titleTextView.text = alertDetail.title
            descriptionTextView.text = alertDetail.description
            dateTextView.text = alertDate
            alertTypeTextView.text=alertDetail.type


        }
        return rootView
    }


}