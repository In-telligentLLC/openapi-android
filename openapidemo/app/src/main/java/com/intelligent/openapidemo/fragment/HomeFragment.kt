package com.intelligent.openapidemo.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.intelligent.openapidemo.R
import com.intelligent.openapidemo.adapters.CommunityListAdapter
import com.intelligent.openapidemo.viewmodels.CommunitiesViewModel
import com.sca.in_telligent.openapi.data.network.model.ErrorType
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var communityListAdapter: CommunityListAdapter = CommunityListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val communitiesViewModel = ViewModelProvider(this).get(CommunitiesViewModel::class.java)
        activity?.let { communitiesViewModel.getCommunities(it) }
        communitiesViewModel.buildingModels.observe(viewLifecycleOwner) { communitiesList ->

            if (communitiesList.errorType==ErrorType.NONE){

                communityListAdapter.getCommunitiesList(communitiesList.communities)

            }
            else
            {
                Snackbar.make(
                    homeFragmentLayout, getString(R.string.errortype_error),
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Action", null).show()
            }

        }

        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = communityListAdapter

        }



    }



}