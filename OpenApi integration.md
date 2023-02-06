# OpenAPI integration ![Supported android api versions](https://img.shields.io/badge/android%20api%20version-from%2026%20to%2033-brightgreen) [![version](https://img.shields.io/badge/version-5.2-green)](https://mvnrepository.com/artifact/com.in-telligent.openapi/openapi)

**Table of Contents**

- [What is OpenAPI](#what-is-openapi)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Integration](#integration)
- [References](#references)


## What is OpenAPI
This library is built by In-telligent with proprietary code to use below features in your native Android application.
1. <b>Override DND & Silent settings:</b> Override feature will bypass Do not disturb and silent settings of your Android device and allow your application to raise the audible alarms / alerts with loudest sound the device can do.<br>
   <br>Application can bypass in below scenarios:<br>
      a. When the device is in DND Mode<br>
      b. When the device is in Silent Mode<br>
      c. When the device is in DND Mode and silenced<br>
2. <b>Auto-subscribe to nearest communities:</b> In-telligent system comes with geofence based communities to group audience. This feature allows the device to automatically subscribe to all the nearest In-telligent communities. The application will run in the background and listen for the device location change events and auto-subscribe or un-subscribe to respective communities. 
      <br>The Auto-subscribe functionality triggers in two scenarios:<br>
      a. <b>When user moves away from Geofence range:</b> OpenAPI will un-subscribe the user from community.<br>
      b. <b>When user enter a Geofence range:</b> OpenAPI will subscribe the user to the community.<br>
3. <b> Notification translation to user choice language:</b> The OpenAPI can translate the notifications (delivered from in-telligent system) into any language of user choice from over 120+ languages.

## Requirements
- Android OS version 8.0 and above
- Partner Token (Partner token is to authenticate your app as client. If you don’t have the partner token, email us at support@in-telligent.com)

## Getting Started
1. <b>Configuration:</b><br>
   i. Add the OpenAPI dependency in the app/build.gradle file
      ```java
      implementation 'com.in-telligent.openapi:openapi:5.2'
      ```
   ii. Add the following required dependency project/build.gradle file
      ```java
      classpath "io.realm:realm-gradle-plugin:10.11.0"
      ```
   iii.	Add the following dependency as plugin in app/build.gradle file
      ```java
      apply plugin: 'realm-android'
      ```
   iv. Add the following dependencies as libraries in app/build.gradle file
      ```java
      implementation fileTree(dir: 'libs', include: ['*.jar'])
      implementation 'com.google.firebase:firebase-messaging:23.0.5'
      implementation 'androidx.appcompat:appcompat:1.4.2'
      implementation 'com.github.mrmike:ok2curl:0.7.0'
      //reactive
      implementation 'io.reactivex.rxjava3:rxjava:3.0.0'
      implementation 'io.reactivex.rxjava3:rxandroid:3.0.0'
      implementation 'com.squareup.retrofit2:adapter-rxjava3:2.9.0'
      implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
      implementation 'com.google.android.gms:play-services-location:20.0.0'
      implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.1.0'
      // For workmanager
      implementation 'androidx.work:work-runtime:2.7.1'
      ```
   v. Provide required permissions in the Manifest file. 
      ```java
      <uses-permission android:name="android.permission.INTERNET" />
      <!-- Below permissions are required for override DND settings -->
      <uses-permission android:name="android.permission.FLASHLIGHT" />
      <uses-permission android:name="android.permission.VIBRATE" />
      <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
      <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

      <!-- Below permissions are required for auto-subscription, and Location access -->
      <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
      <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
      <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
      <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
      ```
   vi. Inside the application class, write below code in onCreate() method:
      ```java
      RxJavaPlugins.setErrorHandler(throwable -> PrintLog.print("Rx Error",throwable));
      ```
2. <b>Initialization:</b> Inside the Application class make a call to OpenAPI.init() to do the initialisation.
   ```java
   OpenAPI.Configuration configuration = new OpenAPI.Configuration.Builder()
   .setAppVersion(BuildConfig.VERSION_CODE)
   .setDebug(true)
   .setEnvironment(Environment.RELEASE) // Environment.DEV
   .setHeadsUpNotificationActionReceiver(new CallReceiver())
   .setNotificationOpenActivity<Activity>(Activity::class.java) 
   .build(getApplicationContext());
   
   OpenAPI.init(getApplicationContext(), partnerToken, BuildConfig.APPLICATION_ID, configuration);
   ```
3. <b>Authentication:</b> Authentication is mandatory to use any service from OpenAPI.<br>
   i. checkToken - Check if the app is already authenticated.<br>
   ii. authorization - If not authorized already (checkToken returns false), make a call to "authorization" method.
      ```java
      OpenAPI.getInstance().authorization(pushToken, new Consumer<SuccessResponse>() {
       @Override
       public void accept(SuccessResponse status) {
        // If login success, register push token here
       }
      });
      ```
4. <b>RegisterPushToken</b>: (It is required if you are using In-telligent system to send alerts). Generate Device Token as described in the Firebase documentation. Pass this token to the OpenAPI in the initial launch and when the app detected a change in the push token (ideally should check in every app launch).
   ```java
   OpenAPI.getInstance().registerPushToken((token,new Consumer<SuccessResponse>(){
    @Override
    public void accept(SuccessResponse status){
     if (status.isSuccess())
      PrintLog.print(TAG, "Device Token Sent to server");
     else
      PrintLog.print(TAG, "Failed to send. Please try again later.");
    }
   });
   ```
5. <b>Configure Push Notifications</b><br>
   i. Add the Google Services plugin as the last entry in your app level build.gradle file:
      ```java
      apply plugin: 'com.google.gms.google-services'  // Google Play services Gradle plugin 
      ```
   ii. Add google services plugin to root level build.gradle file as below
      ```java
      dependencies {
      classpath 'com.google.gms:google-services:4.3.14'
      // ...
      }
      ```
   iii. Add Firebase to your project as described in the following link: https://firebase.google.com/docs/cloud-messaging/android/client<br>
   iv. Download google-services.json file (from firebase) and place in the app folder of the project structure.<br>
   v. Add the following service to the Manifest file within <service> tag.
      ```java
      <service android:name=".FCMService">
      <intent-filter>
      <action android:name="com.google.firebase.MESSAGING_EVENT" />
      </intent-filter>
      </service>
      ```
   
## Integration
1. <b>Override DND & Silent settings of the device:</b><br>
   On FirebaseService class "onReceiveMessage" method, call OpenAPI.relayPushNotification method with the received payload. The OpenAPI will take care of bypassing the DND, Silent settings, and alert the user with loudest possible sound from the device. (Provide the required permissions as described in getting started section)  
   ```java
   OpenAPI.getInstance().relayPushNotification(remoteMessage.getData(), this, true,errorType -> {
    if (errorType == ErrorType.NOTIFICATION_PERMISSION_NOT_GRANTED){
     PrintLog.print("Tag", "Notification permission not allowed");
    }
   });
   ```
   Note: If you want to use your own notification distribution system (Instead distributing alerts from In-telligent system), please handover the notification to the OpenAPI in the defined format to override alert.

   <b>Notification actions handling:</b>Notification will have 3 options (Open, Mark as read, and Delete). These call actions are to be handled in the application receiver class (Refer to the CallReceiver class in the quickstart application).
   ```java
   // Alert Opened
   OpenAPI.openedAlert(Integer.parseInt(data.getNotificationModel().getId()), successResponse -> {
    //………..
   });

   // Alert Deleted
   OpenAPI.deleteAlert(Integer.parseInt(data.getNotificationModel().getId()),  successResponse -> {
    //………..
   });
   ```
   <b>Enable additional DND permission</b> (Optional) – Some devices have additional DND settings to prevent the alert sounds. To handle the alerts in such devices, we need to request DND additional permission from user. If we enable the permission, upon receiving an emergency alert, the OpenAPI library will turn off the device DND for 60 seconds to alert the user and then turns on the DND again (automatic). Without this permission, the app will work (make audible sound) in all the devices where DND additional settings are not manually enabled.
   ```java
   OpenAPI.checkDNDPermission(this); // Make sure you add required permissions in the Manifest file.
   ```
   
   <b>How to test</b><br>
   <b>Using In-Telligent Portal</b> If you are using In-telligent system to send alerts, proceed with this approach. Else use your own notification system to send alert and test on mobile application.
   1. Launch In-telligent web portal<br>
      a. Prod: https://app.in-telligent.com/<br>
      b. Dev: https://app.uat.in-telligent.com/<br>
   2. Login using valid credentials (Admin / Community Manager).
   3. Select a community from list under Communities tab to send an alert. 
   4. Once community page is opened, click on “send a community alert” from left sidebar navigation. 
   5. Enter required fields, select type of alert and click on send button. 
   6. If everything went well, device should receive the alert and the respective sound. Below is the sample notification payload:
      ```java
      {
       "data": {
        "payload_version":"2",
        "building": {
         “name":  "Test Community",
         "id":"12345"
         },
        "data": {
         "title": "test alert please ignore",
         "body": "test alert please ignore"
         },
        "notification"{
         "action": "pushNotification",
         "language": "en",
         "id": "12345",
         "type": "normal",
         "suffix": "to refer back to this alert or to see more information, please open the App"
         }
         /// Any additional fields you may want
        },
       "to": "fW40hcEO9PA:APA91bFJr2-nwkSuBfhL3_g2Q3iPZOZGcx-P6B_zVVR_7k1zMDC-qFIYcdzdNE_h2ecRO17Sw2tvcHp7xLkAArajyILRAgYolHJt8CuXo7t66TB48VoOeTdQPI-Mtq0HKjxKPXF4S_BM"
      }
      ```
      Description of the above payload parameters:   
      1. building: Contains building details like name (building name) and id (building id)<br>
      2. data: Contains notification data like title (notification title) and body (notification description)<br>
      3. notification: Contains notification parameters like action (notification type), language (notification language), id (notification id), type (alert type) and suffix.<br>
      4. Type can be one of -<br>
           1. life-safety<br>
           2. personal-safety<br>
           3. critical<br>
           4. ping<br>
           5. weather-alert<br>
           6. lightning-alert<br>
           7. pc-urgent<br>
           8. pc-emergency<br>
           9. Normal<br>
           10. suggested<br>
   
2. <b>Auto-subscribe (Auto grouping) to nearest communities</b><br>
   Upon authentication, the following method is used to initiate the auto subscription process. This method internally will auto-subscribe the device to the nearest communities (Geofences) and returns the list of subscribed communities to the client application.
   ```java
   OpenAPI.getSubscribedCommunities (getApplicationContext(), new Consumer<CommunitiesList>() {
    @Override
    public void accept(CommunitiesList communities) {
     // Display the received communities list
    }
   });

   ```
   The above method will handle the following functionality:
   1. Listen and handle location change Events.
   2. Perform communities’ subscription and un-subscription, which includes updating Geofences on the device.
   
   <b>How to test</b><br>
   Create new communities from the web portal
    1. Launch In-Telligent web portal
       1.	Prod: https://app.in-telligent.com/
       2.	Dev: https://app.uat.in-telligent.com/
    2. Login using valid credentials (Community Manager / Admin).
    3. Create new community from communities tab.
    4. Newly created community should be auto-subscribed and listed in device on location changed / on app launch / after a specific time period (Syncing the latest changes is expected to happen in max of 12 hours in the client application).
    5. Note: Sync frequency in production environment is set to 12 hours and in Dev it is set to 15 mins.

3. <b>Language translation</b><br>
   Upon authentication, you can translate in-telligent delivered notifications to any other supported language.
   To get supported languages
   ```java
   OpenAPI.getLanguages(new Consumer<NotificationLanguageResponse>() {
    @Override
    public void accept(NotificationLanguageResponse languageResponse) throws Exception {
     System.out.println(languageResponse.getLanguages());
    }
   });

   ```
   To translate the alert into supported languages
   ```java
   OpenAPI.getTranslation(id,languageValue,new Consumer<TranslationResponse>() {
    @Override
    public void accept(TranslationResponse translationResponse) throws Exception {
     System.out.println(translationResponse.getBody());
     System.out.println(translationResponse.getTitle());
    }
   });

   ```
   
## References
1. https://firebase.google.com/docs/cloud-messaging/android/client
2. https://chrome.google.com/webstore/detail/restlet-client-rest-api-t/aejoelaoggembcahagimdiliamlcdmfm?hl=en
3. https://console.firebase.google.com
4. https://firebase.google.com/docs/cloud-messaging/server
