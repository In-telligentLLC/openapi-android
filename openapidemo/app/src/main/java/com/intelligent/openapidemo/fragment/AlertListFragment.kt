package com.intelligent.openapidemo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.intelligent.openapidemo.R
import com.intelligent.openapidemo.adapters.AlertListAdapter
import com.intelligent.openapidemo.viewmodels.AlertListViewModel
import com.sca.in_telligent.openapi.OpenAPI
import kotlinx.android.synthetic.main.fragment_alert_list.view.*


class AlertListFragment : Fragment() ,AlertListAdapter.ItemClick {

    companion object {

        private const val ARG_PARAM_BUILDING_ID = "buildingId"
        fun newInstance(buildingId: Int): AlertListFragment {
            val bundle = Bundle()
            bundle.putInt(ARG_PARAM_BUILDING_ID, buildingId)
            val alertListFragment = AlertListFragment()
            alertListFragment.arguments = bundle
            return alertListFragment


        }

    }

    private var buildingId = -1
    private var alertListAdapter: AlertListAdapter = AlertListAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            buildingId = it.getInt(ARG_PARAM_BUILDING_ID)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_alert_list, container, false)
        rootView.recyclerView_Alert_List.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = alertListAdapter

        }


        val alertListScreenViewModel =
            ViewModelProvider(this).get(AlertListViewModel::class.java)
        activity?.let { alertListScreenViewModel.getAlertList(it, buildingId) }
        alertListScreenViewModel.communityAlert.observe(viewLifecycleOwner) { communitiesAlertList ->
            Log.i("TEST", "Alertlist data")


            alertListAdapter.getCommunitiesAlertList(communitiesAlertList.notifications)


        }
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun openCommunityAlert(notificationId: Int) {
        OpenAPI.openedAlert(notificationId) { response ->
            if (response != null && response.isSuccess) {


            }

        }

    }
}