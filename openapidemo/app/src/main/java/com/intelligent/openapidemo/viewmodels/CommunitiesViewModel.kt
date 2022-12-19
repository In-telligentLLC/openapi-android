package com.intelligent.openapidemo.viewmodels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.data.network.model.CommunitiesList

class CommunitiesViewModel: ViewModel() {

    val buildingModels = MutableLiveData<CommunitiesList>()


    fun getCommunities(activity: FragmentActivity) {

        OpenAPI.getSubscribedCommunities(activity) { result ->

                buildingModels.postValue(result)

        }
    }
}




