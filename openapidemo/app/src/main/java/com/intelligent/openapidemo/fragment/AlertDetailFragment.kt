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

        val titleTextView: TextView = rootView.findViewById(R.id.communitytitle)
        val descriptionTextView: TextView = rootView.findViewById(R.id.communitydescription)
        val dateTextView: TextView = rootView.findViewById(R.id.communitydate)
        val alertTypeTextView: TextView = rootView.findViewById(R.id.communitytype)


        val alertDetailScreenViewModel =
            ViewModelProvider(this).get(AlertDetailViewModel::class.java)
        activity?.let { alertDetailScreenViewModel.getAlertDetail(notificationId) }
        alertDetailScreenViewModel.communityAlertDetail.observe(viewLifecycleOwner) { communitiesAlertDetail ->

            titleTextView.text = communitiesAlertDetail.title
            descriptionTextView.text = communitiesAlertDetail.description
            dateTextView.text = communitiesAlertDetail.date.toString()
            alertTypeTextView.text = communitiesAlertDetail.type


        }
        return rootView
    }


}