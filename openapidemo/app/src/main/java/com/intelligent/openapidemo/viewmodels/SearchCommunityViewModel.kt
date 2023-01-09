package com.intelligent.openapidemo.viewmodels

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.data.network.model.CommunitiesList

class SearchCommunityViewModel : ViewModel() {

    val buildingModels = MutableLiveData<CommunitiesList>()


    @SuppressLint("SuspiciousIndentation")
    fun getCommunities(activity: FragmentActivity, data: String) {

        OpenAPI.getCommunities(activity) { result ->
            val communitiesList =
                CommunitiesList(result.communities.filter { it.name.contains(data, true) })
            communitiesList.errorType = result.errorType
            buildingModels.postValue(communitiesList)
        }


    }
}