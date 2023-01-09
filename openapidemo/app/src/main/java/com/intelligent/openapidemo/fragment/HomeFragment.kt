package com.intelligent.openapidemo.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.intelligent.openapidemo.R
import com.intelligent.openapidemo.adapters.CommunityListAdapter
import com.intelligent.openapidemo.viewmodels.CommunitiesViewModel
import com.sca.in_telligent.openapi.data.network.model.ErrorType
import kotlinx.android.synthetic.main.fragment_home.*


 class HomeFragment : Fragment(R.layout.fragment_home), CommunityListAdapter.ItemClick{

     private var communityListAdapter: CommunityListAdapter = CommunityListAdapter(this)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = communityListAdapter

        }

        val communitiesViewModel = ViewModelProvider(this).get(CommunitiesViewModel::class.java)
        activity?.let { communitiesViewModel.getCommunities(it) }
        communitiesViewModel.buildingModels.observe(viewLifecycleOwner) { communitiesList ->


            if (communitiesList.errorType == ErrorType.NONE) {

                communityListAdapter.getCommunitiesList(communitiesList.communities)

            } else {
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

    }




 }
     override fun buildingSelected(buildingId: Int) {
         activity?.let {  it.supportFragmentManager.beginTransaction()
             .replace(R.id.fragmentContainer,AlertListFragment.newInstance(buildingId))
             .addToBackStack(null)
             .commit()

         }

     }



    }





