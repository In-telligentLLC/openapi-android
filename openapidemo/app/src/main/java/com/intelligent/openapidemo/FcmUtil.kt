package com.intelligent.openapidemo

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.rxjava3.core.Observable
class FcmUtil {

    companion object {
        var fcmToken: Observable<String> = Observable.just("NA")
        fun getFcmToken() {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                fcmToken = Observable.just(task.result)


                if(task.result != null && task.result.isNotEmpty())
                    TestApplication.pushToken = task.result
            })
        }
    }

}