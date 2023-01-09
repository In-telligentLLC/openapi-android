package com.intelligent.openapidemo.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intelligent.openapidemo.fragment.HomeFragment
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.data.network.model.NotificationsList

class AlertListViewModel : ViewModel() {

    val communityAlert = MutableLiveData<NotificationsList>()


    fun getAlertList(activity: FragmentActivity, buildingID: Int) {

        OpenAPI.getNotificationsByBuilding(buildingID) { result ->

            communityAlert.postValue(result)

        }


    }
}


