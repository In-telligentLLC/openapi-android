package com.intelligent.openapidemo.viewmodels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.data.network.model.Notification
import com.sca.in_telligent.openapi.data.network.model.NotificationLanguageResponse

class AlertDetailViewModel : ViewModel() {

    val alertDetail = MutableLiveData<Notification>()
    var notificationTitle = MutableLiveData<String>()
    var notificationDescription = MutableLiveData<String>()
    var notificationLanguages = MutableLiveData<NotificationLanguageResponse>()

    fun getAlertDetail(notificationId: Int) {

        OpenAPI.getCompleteNotification(notificationId) { response ->
            alertDetail.postValue(response)
        }

    }
    fun getTranslation(notificationId: String, language: String) {

        OpenAPI.getTranslation(notificationId, language) { translationResponse ->
            notificationTitle.postValue(translationResponse.title)
            notificationDescription.postValue(translationResponse.body)
        }

    }
    fun getLanguages() {

        OpenAPI.getLanguages { notificationLanguageResponse ->
            notificationLanguages.postValue(notificationLanguageResponse)
        }
    }

}