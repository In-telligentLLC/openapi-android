package com.intelligent.openapidemo.viewmodels
import android.content.Context
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

    fun getAlertDetail(notificationId: Int,context: Context) {

        OpenAPI.getCompleteNotification(notificationId,context) { response ->
            alertDetail.postValue(response)
        }

    }
    fun getTranslation(notificationId: String, language: String,context: Context) {

        OpenAPI.getTranslation(notificationId, language,context) { translationResponse ->
            notificationTitle.postValue(translationResponse.title)
            notificationDescription.postValue(translationResponse.body)
        }

    }
    fun getLanguages(context: Context) {

        OpenAPI.getLanguages (context){ notificationLanguageResponse ->
            notificationLanguages.postValue(notificationLanguageResponse)
        }
    }

}