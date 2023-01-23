package com.intelligent.openapidemo.fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.intelligent.openapidemo.R
import com.intelligent.openapidemo.adapters.SearchCommunityAdapter
import com.intelligent.openapidemo.viewmodels.SearchCommunityViewModel
import com.sca.in_telligent.openapi.data.network.model.ErrorType
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search_community.view.*


class SearchCommunityFragment : Fragment(R.layout.fragment_search_community) {




    private var searchCommunityAdapter: SearchCommunityAdapter = SearchCommunityAdapter()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val rootView = inflater.inflate(R.layout.fragment_search_community, container, false)

        rootView.recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = searchCommunityAdapter

        }
        val communitiesViewModel = ViewModelProvider(this).get(SearchCommunityViewModel::class.java)
        val editText = rootView.findViewById(R.id.search_edit_text) as EditText


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(searchText: CharSequence?, p1: Int, p2: Int, charCount: Int) {

               if(charCount >= 3 )
                activity?.let { communitiesViewModel.getCommunities(it, searchText.toString()) }
             

            }


            override fun afterTextChanged(p0: Editable?) {
                searchCommunityAdapter.communities.clear()
                searchCommunityAdapter.notifyDataSetChanged()



            }


        }
        editText.addTextChangedListener(textWatcher)

        communitiesViewModel.buildingModels.observe(viewLifecycleOwner) { communitiesList ->

            if (communitiesList.errorType==ErrorType.NONE){

                searchCommunityAdapter.getCommunitiesList(communitiesList.communities)

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

return rootView

    }


}