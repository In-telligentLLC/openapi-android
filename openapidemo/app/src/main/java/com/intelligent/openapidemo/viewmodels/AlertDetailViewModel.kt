package com.intelligent.openapidemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.data.network.model.Notification

class AlertDetailViewModel:ViewModel() {

    val alertDetail = MutableLiveData<Notification>()


    fun getAlertDetail(notificationId: Int) {

        OpenAPI.getCompleteNotification(notificationId){response->

            alertDetail.postValue(response)
        }

    }
}