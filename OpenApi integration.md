# OpenAPI integration ![Supported android api versions](https://img.shields.io/badge/android%20api%20version-from%2026%20to%2033-brightgreen) [![version](https://img.shields.io/badge/version-5.2-green)](https://mvnrepository.com/artifact/com.in-telligent.openapi/openapi)

**Table of Contents**

- [Purpose](#purpose)
>- [Override DND](#override-dnd)
>- [Auto Subscribe](#auto-subscribe)
>- [Language Translation](#purpose-language-translation)
- [Requirements (Development Environment)](#requirements-development-environment)
>- [Android](#requirements-android)
>- [iOS](#requirements-ios)
- [Setup Environment](#setup-environment)
>- [Android](#setup-android)
>- [iOS](#setup-ios)
- [Getting Started](#getting-started)
>- [Android](#start-android)
>- [iOS](#start-ios)
- [About The Framework](#about-the-framework)
>- [Override device DND to receive push messages](#override-device-dnd-to-receive-push-messages)
> >- [Android](#override-dnd-android)
> >- [How to test](#test-override-dnd-android)
> >- [iOS](#override-dnd-ios)
> >- [How to test](#test-override-dnd-ios)
>- [Auto-subscribe (Auto grouping) to nearest communities](#auto-subscribe-auto-grouping-to-nearest-communities)
> >- [Android](#auto-subscribe-android)
> >- [How to test](#test-auto-subscribe-android)
> >- [iOS](#auto-subscribe-ios)
> >- [How to test](#test-auto-subscribe-ios)
>- [Language translation](#language-translation)
> >- [Android](#language-translation-android)
> >- [How to test](#test-language-translation-android)
> >- [iOS](#language-translation-ios)
> >- [How to test](#test-language-translation-ios)
>- [Send alerts to all subscribers](#send-alerts-to-all-subscribers)
> >- [Android](#send-alerts-android)
> >- [How to test](#test-send-alerts-android)
> >- [iOS](#send-alerts-ios)
> >- [How to test](#test-send-alerts-ios)
>- [Delivered alert status of an alert](#delivered-alert-status-of-an-alert)
>- [Opened alert status](#opened-alert-status)
> >- [Android](#alert-opened-android)
> >- [iOS](#alert-opened-ios)
>- [Delete an alert](#delete-an-alert)
> >- [Android](#delete-alert-android)
> >- [iOS](#delete-alert-ios)
- [Integration with In-Telligent](#integration-with-in-telligent)
- [References](#references)


## Purpose
This document is to provide guidance on how to integrate In-Telligent’s Open API Library (Framework) with your Android and iOS mobile applications.
The Open API library has 3 features:

### Override DND:
DND Override feature will override the DND on the targeted device (iOS and Android) and raises the alarms / alerts for the following scenarios:
1. Device is in DND Mode
2. Device is in Silent Mode
3. Device is in DND Mode and manually silenced
4. Device is in DND Mode and the App is in idle state
5. Device is in DND Mode and the App is in killed state
6. Device is in DND Mode and the App is running in background
7. Device is in DND Mode and the App is running in foreground mode
8. Device is in DND Mode and Flight mode, and WIFI is enabled

### Auto Subscribe:
Also known as Auto Grouping. This feature allows the device to automatically subscribe to all the nearest In-Telligent communities. This service will run in the background and automatically subscribes to nearest communities in following scenarios:
1. When the app is in background state
2. When the app is in foreground state
3. When the app is in killed state

### Language Translation: <a id='purpose-language-translation'></a>
The alert notification can be translated into a different language for better reading in a comfortable language.

## Requirements (Development Environment)
### Android: <a id='requirements-android'></a>
- Android Studio
- Android Mobile Version 8.0 and above
- Open API library added to build.gradle file
- Partner Token
- Google account
  + Server-side implementation – (App Owner’s server)
    + Ensure the App Owner’s server API implementation is utilizing Google firebase as described in the following link:
      https://firebase.google.com/docs/cloud-messaging/server
- The notification payload should be created as per sample listed in Section 6 Server-side implementation

### iOS: <a id='requirements-ios'></a>
- Xcode 12 above
- iOS Mobile Version 12.0 and above
- Open API library file *
- Partner Token
- Apple developer account
  + Server-side implementation -
    + Ensure the App Owner’s server API implementation is ready to send notification payload as per the sample listed in section 6
* Supplied by In-Telligent

## Setup Environment
The following describes the required Android and iOS integration dependencies for the OpenAPI library.

### Android <a id='setup-android'></a>
1. Open the Android Studio project.
2. Add the following dependencies as libraries: (app: build.gradle file dependencies section):
   ```java
   implementation 'com.in-telligent.openapi:openapi:5.2'
   ```
3. Add the following dependency as library: (project: build.gradle file dependencies section)
   ```java
   classpath "io.realm:realm-gradle-plugin:10.11.0"
   ```
4. Add the following dependencies as plugin: (app: build.gradle file):
   ```java
   apply plugin: 'realm-android'
   ```
5. Add the following dependencies as libraries: (app: build.gradle file dependencies section):
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
6. Add the logo property to the application tag and assign the path of the icon for notification in AndroidManifest.xml. Example:
   ```java
   <application
   …
   android:logo="@mipmap/ic_launcher_round"
   …
   ></application>
   ```
7. Add following code to onCreate() method of application class:
   ```java
   RxJavaPlugins.setErrorHandler(throwable -> PrintLog.print("Rx Error",throwable));
   ```

### iOS <a id='setup-ios'></a>
1. Open the XCode project in which you would like to integrate this framework.
2. Drag and drop “OpenAPI.framework” into the bundle folder.
3. Make sure this framework is added in both “Embedded Binaries” and “Linked Framework and libraries”
4. Import the following third-party dependency libraries:
   ```swift
   pod 'Alamofire', '~> 4.9.1’
   pod "RealmSwift", "3.17.3"
   pod "SwiftyJSON", "~> 5.0.0"
   pod 'DeviceGuru'
   ```
5. Install the above dependency frameworks by using cocoa pods.
   Ref: https://guides.cocoapods.org/using/getting-started.html
6. Add .mp3 files into your project bundle which are available in the framework zip folder.
7. Add OpenAPI Framework in Link Binary with Libraries at Build Phases and make optional (Other wise it leads to crash after build generation)
8. To generate the build please follow below steps
+ Select Project Target > Edit Scheme > Archive > Pre-Actions
+ Add Script
+ Provide build settings from – Select the App Target
+ Add the below Script
   ```swift
   FRAMEWORK="OpenAPI"
   FRAMEWORK_EXECUTABLE_PATH="${BUILT_PRODUCTS_DIR}/${FRAMEWORKS_FOLDER_PATH}/$FRAMEWORK.framework/$FRAMEWORK"
   EXTRACTED_ARCHS=()
   for ARCH in $ARCHS
   do
   lipo -extract "$ARCH" "$FRAMEWORK_EXECUTABLE_PATH" -o "$FRAMEWORK_EXECUTABLE_PATH-$ARCH"
   EXTRACTED_ARCHS+=("$FRAMEWORK_EXECUTABLE_PATH-$ARCH")
   done
   lipo -o "$FRAMEWORK_EXECUTABLE_PATH-merged" -create "${EXTRACTED_ARCHS[@]}"
   rm "${EXTRACTED_ARCHS[@]}"
   rm "$FRAMEWORK_EXECUTABLE_PATH"
   mv "$FRAMEWORK_EXECUTABLE_PATH-merged" "$FRAMEWORK_EXECUTABLE_PATH"
   ```

## Getting Started
### Android <a id='start-android'></a>
1. Initialize Open API
   The following method call need to be added to the Application class and performed one time on initial launch of the mobile application
   Make a call to OpenAPI.init() method from Open API
   ```java
   OpenAPI.Configuration configuration = new OpenAPI.Configuration.Builder()
   .setAppVersion(BuildConfig.VERSION_CODE)
   .setDebug(true)
   .setEnvironment(Environment.RELEASE)
   .setHeadsUpNotificationActionReceiver(new CallReceiver()).build(getApplicationContext());
   OpenAPI.init(getApplicationContext(), partnerToken, BuildConfig.APPLICATION_ID, configuration);
   ```
   By default, OpenAPI library would be connecting to Production servers. Environment.RELEASE is the default configuration. You can change to Environment.DEV, to switch between Prod and Dev servers.
   ```java
   OpenAPI.Configuration configuration = new OpenAPI.Configuration.Builder()
   .setAppVersion(BuildConfig.VERSION_CODE)
   .setDebug(true)
   .setEnvironment(Environment.DEV)
   .setHeadsUpNotificationActionReceiver(new CallReceiver()).build(getApplicationContext());
   OpenAPI.init(getApplicationContext(), partnerToken, BuildConfig.APPLICATION_ID, configuration);
   ```
2. Authentication: Before we call any service from OpenAPI, we need to authenticate the Partner token which was received from In-Telligent.
   There are 2 methods for the authentication process:
    1. CheckToken: – This is a method which looks for the authentication token in the application cache. (This will be by default false in the first run).
    2. authorization: - If the authentication token is not available, checkToken method returns false. Then a call to “authorization” method is required to get the authentication token and store it in cache.
       **Parameters:**
       **pushToken –**
       Required - Yes
       Type - String
   ```java
   OpenAPI.getInstance().authorization(pushToken, new Consumer<SuccessResponse>() {
   @Override
   public void accept(SuccessResponse status) {
    if (status.isSuccess) {
     PrintLog.print(TAG, "Sign in received success");
     // register push token here
     } else {
       PrintLog.print(TAG, "Sign in received failure");
     }
    }
   });
   ```

### iOS <a id='start-ios'></a>
1. Authentication: Before we call any service from OpenAPI, we need to authenticate the Partner token which was received from In-Telligent.
   There are 2 methods for the authentication process:
   1. CheckToken: – This is a method which looks for the authentication token in the application cache. (This will be by default false in the first run).
   2. authorization: - If the authentication token is not available, checkToken method returns false. Then a call to “authorization” method is required to get the authentication token and store it in cache.
   **Parameters:**
   **partnerToken –**
   Required - Yes
   Type - String
   **email/mobileNumber –**
   Required - Yes
   Type - String
   **password –**
   Required - Yes
   Type - String
   **name –**
   Required – Yes (Name or empty string)
   Type – String
   Default – If empty, default is email/mobilenumber
   **gender –**
   Required – Yes
   Type – OpenAPIUserGender (OpenAPI library class)
      ```swifty
      OpenAPI.authorization(
      partnerToken: partnerToken,
      email: emailId,
      mobileNumber: mobileNumber,
      password: password,
      name: username,
      gender: .male){ status in
       if status {
        // login success
       } else {
        // something went wrong
       }
      }
      ```

## About The Framework
The In-Telligent Open API framework supports the integration of In-Telligent proprietary code into any mobile application. Features include customer authentication, sending push notifications to subscribed users, automatically grouping users into “communities” based on real-time location and translation of text into any supported language.

### Override device DND to receive push messages
This feature overrides a mobile device’s DND and silent mode features in order to force an audible alert to that device.

#### Android <a id='override-dnd-android'></a>
1. Add the Google Services plugin as the last entry in your app level build.gradle file:
   ```java
   apply plugin: 'com.google.gms.google-services'  // Google Play services Gradle plugin 
   ```
2. Add google services plugin to root level build.gradle file as below
   ```java
   dependencies {
   classpath 'com.google.gms:google-services:4.3.14'
   // ...
   }
   ```
3. Add Firebase to your project as described in the following link:
   https://firebase.google.com/docs/cloud-messaging/android/client
4. Download google-services.json file (from firebase) and place in the app folder of the project structure.
5. Note down the firebase project server key from the project created in Firebase console. (Project settings -> Cloud Messaging -> Server Key).
   **Note:** This server key is used in later steps to send notification from Server / rest client
6. Below is the callback receiver and call actions in the application receiver from the library.
   ```java
   public class CallReceiver extends HeadsUpNotificationActionReceiver {
   @Override
   public void onReceive(Context context, Intent intent) {
   super.onReceive(context, intent);
   if (intent != null && intent.getExtras() != null) 
   {
    String action =  intent.getAction();
    PushNotification data =  (PushNotification) intent.getSerializableExtra(Actions.CALL_NOTIFICATION);
    NotificationManager notificationManager = (NotificationManager)context.
    getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.cancel(Integer.parseInt(data.getNotificationModel().getId()));
    OpenAPI.getInstance().getAudioHelper().stopRingtone();
    if (action != null) 
    {
     if (action.equals(Actions.ACTION_MARK_AS_READ) && data != null) 
     {
      PrintLog.print("CallReceiver", "Mark as Read action");
      OpenAPI.openedAlert(Integer.parseInt(data.getNotificationModel().getId()), successResponse -> 
      {
       if (successResponse != null && successResponse.isSuccess()) 
       {
        Toast.makeText(context, “Alert open success”),   Toast.LENGTH_LONG).show();
       }
       else if (successResponse != null) 
       {
        Toast.makeText(context, successResponse.getError(),  Toast.LENGTH_LONG).show();
       }
       else 
       {
        Toast.makeText(context, “Alert open failed”, Toast.LENGTH_LONG).show();
       }
      });
     } 
     else if (action.equals(Actions.ACTION_DELETE)) 
     {
      PrintLog.print("CallReceiver", "Delete action");
      OpenAPI.deleteAlert(Integer.parseInt(data.getNotificationModel().getId()),  successResponse -> {
      if (successResponse != null && successResponse.isSuccess()) 
      {
       Toast.makeText(context, context.getString(R.string.alert_delete_success),   Toast.LENGTH_LONG).show();
      } 
      else if (successResponse != null) 
      {
       Toast.makeText(context, successResponse.getError(),  Toast.LENGTH_LONG).show();
      } 
      else 
      {
       Toast.makeText(context, “Delete alert failed”,  Toast.LENGTH_LONG).show();
      }
     });
    } 
    else if (action.equals(Actions.ACTION_OPEN)) 
    {
     PrintLog.print("CallReceiver", "Open action");
    }
   }
   ```
    3. Generate Device Token as described in the Firebase SDK link provided above
    4. This token is required to pass on to In-Telligent servers to send push notifications to the device. Below code snippet shows how to update the FCM device token to Server on token refresh.
   ```java
   OpenAPI.getInstance().registerPushToken((token,new Consumer<SuccessResponse>() 
   { 
    @Override
    public void accept(SuccessResponse status) 
    {
     if (status.isSuccess())
      PrintLog.print(TAG, "Device Token Sent to server");
     else 
      PrintLog.print(TAG, "Failed to send. Please try again later.");
    }
   });
   ```
7. Enable additional DND permission –
   Some devices have additional DND settings to prevent the alert sounds. To handle the alerts in such devices, we need to request DND additional permission from user. If we enable the permission, the OpenAPI library will turn off the device DND for 60 seconds to play sound and then turns on the DND again (automatic).
   Make a call to the below method in the home activity to request this special permission. Without this permission also the app will work (make audible sound) in all the devices where DND additional settings are not manually enabled.
   ```java
   OpenAPI.checkDNDPermission(this);
   ```
8. Add the following permissions to the Manifest file (AndroidManifest.xml)
   ```java
   <uses-permission android:name="android.permission.FLASHLIGHT" />
   <uses-permission android:name="android.permission.VIBRATE" />
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
   <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
   ```
9. Add the following service to the Manifest file within <application> tag
   FCMService is a the class which extends FirebaseMessagingService. The onMessageReceived method is called when a pushed notification is received.
   ```java
   <service android:name=".FCMService">
   <intent-filter>
   <action android:name="com.google.firebase.MESSAGING_EVENT" />
   </intent-filter>
   </service>
   ```
10. Runtime location permissions is required for authorization if autosubscription is enabled.
11. Setup the required Firebase methods as detailed in the following link:  
    https://firebase.google.com/docs/cloud-messaging/android/receive
    Override the onReceiveMessage method to receive the message payload and call below “relayPushNotification” method with message payload in the onReceiveMessage of FirebaseService.
    ```java
    OpenAPI.getInstance().relayPushNotification(remoteMessage.getData(), this, true,errorType -> {
     if (errorType == ErrorType.NOTIFICATION_PERMISSION_NOT_GRANTED){
      PrintLog.print("Tag", "Notification permission not allowed");
     }
    });
    ```
    **Note:** The notification will have three button option –
    1. Mark As Read
    2. Delete
    3. Open

    The variations are –
    1. In some devices these buttons will be visible by default.
    2. In some devices we need to expand notification to see the buttons.

    The notification is in expandable form with following variations –
    1. In some devices notification is expanded by default.
    2. In some devices there will be arrow to expand.
    3. In some devices on dragging down notification will expand.

##### How to test <a id='test-override-dnd-android'>
**Using In-Telligent Portal**
You can use the In-Telligent portal to send notifications once section 7 (below) has been fully integrated on the In-Telligent servers.
1. Launch In-Telligent web portal
    1. Prod: https://app.in-telligent.com/
    2. Dev: https://app.uat.in-telligent.com/
2. Login using valid credentials.
3. Click on “Communities” tab from top navigation.
4. Select community from list to send alert notification.
5. Once community page is opened, click on “send a community alert” from left sidebar navigation.
6. Enter required fields, select type of alert and click on send button to send to subscribed users for selected community.
7. Alert should send to the selected/all users in community.
   **Using REST client**
   The following approach can be used to test third-party application functionality without interaction with the In-Telligent messaging service.
   **NOTE:** The In-Telligent messaging service can also be used for testing purposes once the third-party application has been fully integrated with the In-Telligent backend services.  See section 7 below for more information.
1. Use REST client to send notification to the device
    + Install Chrome extension from the following link. https://chrome.google.com/webstore/detail/restlet-client-rest-api-t/aejoelaoggembcahagimdiliamlcdmfm?hl=en
    + Download the FCM sample request (attached here: FCM_Payload.json) and import into REST client (import json as rest client repository)

    + Replace the value for the "to" field with the generated device token (step 5.1), replace the value for the “key” field with the server key (noted in step 5.1) and send notification.
      **Note:** For actual implementation on the server (app owner server), please go through the below documentation.
      https://firebase.google.com/docs/cloud-messaging/server
2. Print and capture the device token from Log as soon as you run the application on the device.
3. Use this device token, the server key (noted in section 5.1) and the following payload to send the notification.
4. Device will receive the alert and the respective sound. If the app developer wants to show the alert within the application, please capture the details from the payload and display accordingly.
   A sample notification payload should be as follows:
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
    + Payload_version: Current version is “2”
    + building: Contains building details like name (building name) and id (building id)
    + data: Contains notification data like title (notification title) and body (notification description)
    + notification: Contains notification parameters like action (notification type), language (notification language), id (notification id), type (alert type) and suffix.
    + Type can be one of  -
        1. life-safety
        2. personal-safety
        3. critical
        4. ping
        5. weather-alert
        6. lightning-alert
        7. pc-urgent
        8. pc-emergency
        9. Normal
        10. suggested
    + To: This is the Device Token generated on the Android device as soon as you run the application. This will be unique to a device. If the same app runs on a different device, the generated device token will be different.
    
#### iOS <a id='override-dnd-ios'></a>
1. In the project target folder -> Capabilities -> background modes enable the following:
    + Audio, Airplay, and Picture in Picture
    + Location updates
    + Voice over IP
    + Background fetch
    + Remote notifications
2. In Capabilities, enable Push notifications.
3. Register the application with the bundle identifier in apple developer website (https://developer.apple.com/) and enable VOIP services on the apple developer portal with the app ID and then create:
    1. Certificates: (Refer to the attached document - iOS Certificates creation process)
    2. Profiles: (Refer to the attached document - iOS Certificates creation process)
    3. Integrate: Install certificates by double clicking on the certificates. This is to run the development application in iPhone physical device and to send push notification using Pusher client.
       Ref: (attached document – iOS Certificates creation process)
4. For VOIP services,
   + Generate a P8 file in KEYS section in developer portal (Ref: attached document – iOS Certificates creation process)
   + Download the generated file
   + Integrate the above certificates with backend web services, which will initiate the notification (app owner’s server)
5. For further use, need to capture device tokens from the following delegates methods
   ```swift
   func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken
   deviceToken: Data){
   let deviceTokenString = deviceToken.reduce("", {$0 + String(format: "%02X", $1)})
   print("Pushtoken regular: \(deviceTokenString)")
   }

   func pushRegistry(_ registry: PKPushRegistry, didUpdate credentials: PKPushCredentials, for type: PKPushType){
   var pToken: String = ""
    for i in 0 ..< credentials.token.count {
     pToken += String(format: "%02.2hhx", credentials.token[i] as CVarArg)
    }
    print("Pushtoken voip: \(pToken)")
   }
   ```
7. In Order to receive PushKit notification / Regular notification from In-Telligent server, need to register captured device tokens (Regular Push token and VoIP Push token) with In-Telligent using OpenAPI method “registerPushKitToken” as bellow,
   ```swift
   OpenAPI.registerPushKitToken(voipPushToken: voipPushToken, regularPushToken: regularPushToken){
    (status) in 
    completion(status)
   }
   ```
8. When the Regular push notification payload is received on the delegate method, call the Open API method relayPushKitNotification and pass the received payload. The Open API library will handle the notification with the appropriate sound.
   ```swift
   func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable: Any], fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void){
    print(userInfo)
    OpenAPI.relayPushKitNotification(dictionaryPayload: userInfo)
   }
   ```
9. When the PushKit payload is received on the delegate method, call the Open API method relayPushKitNotification and pass the received payload.   The Open API library will handle the notification with the appropriate sound.
   ```swift
   func pushRegistry(_ registry: PKPushRegistry, deidReceiveIncomingPushWith rawPayload: PKPushPayload, for type: PKPushType){
    debugPrint("received push notification \(rawPayload.dictionaryPayload)")
    // OpenAPI will parse your payload and play alert based on alert type.
    OpenAPI.relayPushKitNotification(dictionaryPayload: rawPayload.dictionaryPayload)
   }
   ``` 

##### How to test <a id='test-override-dnd-ios'>
Using In-Telligent Portal:
You can use the In-Telligent portal to send notifications once section 7 (below) has been fully integrated on the In-Telligent servers.
1. Launch In-Telligent web portal
    1. Prod: https://app.in-telligent.com/
    2. Dev: https://app.uat.in-telligent.com/
2. Login using valid credentials.
3. Click on “Communities” tab from top navigation.
4. Select community from list to send alert notification.
5. Once community page is opened, click on “send a community alert” from left sidebar navigation.
6. Enter required fields, select type of alert and click on send button to send to subscribed users for selected community.
7. Alert should send to the selected/all users in community.
8. If it is critical alert(Ping, Critical, LSA,PC LSA, Weather, Lightning), it should come with VOIP call otherwise it should come with regular push notification
   Using Pusher Client
   The following approach can be used to test third-party application functionality without interaction with the In-Telligent messaging service.
   NOTE: The In-Telligent messaging service can also be used for testing purposes once the third-party application has been fully integrated with the In-Telligent backend services.  See section 7 below for more information.
1. Use Pusher client to send a notification to the device
   Ref: https://github.com/noodlewerk/NWPusher
2. Print and capture the device token from Log as soon as you run the application on the device.
3. Use this device token, select certificate related to the bundle ID and the sample payload shown below to send the notification.
4. The device will receive the alert and the respective sound. If the app developer wants to show the alert within the application, please capture the details from the payload and display accordingly.
   Sample payload: Regular Push
   ```swift
   {
    "payload_version": 2,
    "notification": {
     "id": "18049",
     "type": "normal",
     "language": "en",
     "suffix": null,
     "action": "pushNotification"
    },
    "building": {
     "id": "10097",
     "name": "Buidling name"
    },
    "topic": "com.intelligent.OpenAPIDemo",
    "aps": {
     "alert": {
      "title": "test8",
      "body": "Hola Veera. Esta es una alerta de prueba de la India. Espero que hayas recibido esto. Si usted puede leer esto. Puedes ir a Panamá."
     },
    "badge": 1,
    "mutable-content": 1,
    "sound": "default"
    }
   }
   ```

   Sample payload: VoIP Push
   ```swift
   {
   "payload_version": 2,
    "notification": {
     "id": "2776123",
     "type": "critical",
     "language": "en",
     "suffix": "to refer back to this alert or to see more information, please open the App",
     "action": "pushNotification"
    },
    "building": {
     "id": "16012",
     "name": "Building name"
    },
    "topic": "com.intelligent.OpenAPIDemo.voip",
    "aps": {
     "alert": {
      "title": "test",
      "body": "test"
     },
    "badge": 1,
    "mutable-content": 1
    }
   }
Description of the above payload parameters:
+ id: Any string. Generally, a good practice is to use the bundle ID
+ title: Title of the notification / alert
+ body: Actual content / information of the notification.
+ badge: To update batch count on the app icon
+ type: alert (Requires as type: alert)
+ Type can be one of  -
    1. life-safety
    2. personal-safety
    3. critical
    4. ping
    5. weather-alert
    6. lightning-alert
    7. pc-urgent
    8. pc-emergency
    9. Normal
    10. suggested

### Auto-subscribe (Auto grouping) to nearest communities
This feature allows the device to automatically subscribe to all the nearest In-Telligent communities. This application will run in the background and listen for the device location change events and auto-subscribe or un-subscribe to respective communities.
The Auto-subscribe functionality triggers in two scenarios:
 1. **User moves away from Geo-fence range:** OpenAPI will un-subscribe the user from community.
 2. **User comes into a Geo-fence range:** OpenAPI will subscribe the user to the community.

#### Android <a id='auto-subscribe-android'></a>
The auto subscription feature will subscribe or un-subscribe a device based on device location change events.  Once this functionality has been invoked, every user / device will automatically update into or out of In-Telligent communities when entering or leaving a Geo fence.
To implement this functionality, the following steps must be taken.
1. Auto Subscription: After authentication, the following method is used to initiate the auto subscription process:
   ```java   
   OpenAPI.getSubscribedCommunities (getApplicationContext(), new Consumer<CommunitiesList>() {
   @Override
   public void accept(CommunitiesList communities) {
   if (communities != null) {
    PrintLog.print(TAG, "SubscriberCommunities  received success");
    // Add communities to recyclerview adatpter
    } else {
     PrintLog.print(TAG, "SubscriberCommunitiesreceived failure");
     }
    }
   });
   ```
This will handle the following functionality:
1. Handling location change Events:
   1. Get the device location and listen for subsequent location change events.
   2. Retrieving the Geo fence from server and automatically will add / remove Geo fence boundaries to / from the device.
2. Get communities and Subscribe to new communities list from server.
   1. Get list of communities from In-Telligent server API.
   2. Auto subscribe to the received communities from API.
The above method is to be called after authorization and whenever app needs to start Auto-Subscription.
3. Community lists for a subscriber:
   After auto subscription has been invoked, handle callback method - getSubscribedCommunities () – to get a list of all communities that a device has been auto subscribed.  The following is a code snippet for this method:
   ```java    
   OpenAPI. getSubscribedCommunities (getApplicationContext(),new Consumer<CommunitiesList>() {
    @Override
    public void accept(CommunitiesList communities) {
     if (communities != null) {
      PrintLog.print(TAG, "Subscriber Communities  received success");
     } else {
      PrintLog.print(TAG, "SubscriberCommunitiesreceived failure");
     }
    }
   });
   ```
4. Permissions:
   Add the following Geo Fence services to the application manifest.xml file and then handle the permission request.
   ```java
   <uses-permission android:name="android.permission.FLASHLIGHT" />
   <uses-permission android:name="android.permission.VIBRATE" />
   <uses-permission android:name="android.permission.INTERNET"/>
   <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
   <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
   ```
   
##### How to test <a id='test-auto-subscribe-android'></a>
1. Install and open the sample application in android devices
2. A list of communities (nearby) for which the device is subscribed will be shown. As this auto-subscription is based on the user’s geo location, it shows different results in different locations. If there are no communities near to the user, the device will not be subscribed to any communities.
3. To add new communities for testing purposes:
    1. Launch In-Telligent web portal
        1. Prod: https://app.in-telligent.com/
        2. Dev: https://app.uat.in-telligent.com/
    2. Login using valid credentials.
    3. Click on “Communities” tab from top navigation.
    4. Click on “Add New community” from top navigations.
    5. Fill required fields and click on Save button.
    6. After saving user will navigate to community summary page where user can edit any fields if required.
    7. Newly created community should be auto-subscribed and listed in device on location changed / on app launch / after a specific time period (Currently set to 6 hours).

#### iOS <a id='auto-subscribe-ios'></a>
The auto subscription feature will subscribe or un-subscribe a device based on device location change events.  Once this functionality has been invoked, every user / device will automatically update into or out of In-Telligent communities when entering or leaving a Geo fence.
To implement this functionality, the following steps must be taken.
1. Auto Subscription: After authentication, the following method is used to initiate the auto subscription process:
   OpenAPI.start(self)
   This will handle the following functionality:
    1. Handling location change Events:
        1. Get the device location and listen for subsequent location change events.
        2. Automatically add / remove Geo fence boundaries to / from the device.
    2. Get communities and subscribe to new communities list from server.
        1. Get list of communities from In-Telligent server API.
        2. Auto subscribe to the received communities from API.
        3. Save received communities into device local storage (DB).
           The above method is to be called after authorization and whenever app needs to start Auto-Subscription.
2. Community lists for a subscriber:
   After auto subscription has been invoked, handle delegate method - subscribedCommunities( ) – to get a list of all communities that a device has been auto subscribed.  The following is a code snippet for this method:
   ```swift
   func subscribedCommunities(_ subscribedCommunities: [INCommunity]){
    print(subscribedCommunities)
   }
   ```
3. Permissions:
   Add following permissions to. plist.
   ```swift
   // TODO:   add details
   ```
   
##### How to test <a id='test-auto-subscribe-ios'></a>
1. Install and open the sample application.
2. A list of communities (nearby) for which the device is subscribed will be shown. As this auto-subscription is based on the user’s geo location, it shows different results in different locations. If there are no communities near to the user, the device will not be subscribed to any communities.
3. To add new communities for testing purposes:
    1. Launch In-Telligent web portal
        1. Prod: https://app.in-telligent.com/
        2. Dev: https://app.uat.in-telligent.com/
    2. Login using valid credentials.
    3. Click on “Communities” tab from top navigation.
    4. Click on “Add New community” from top navigations.
    5. Fill required fields and click on Save button.
    6. After saving user will navigate to community summary page where user can edit any fields if required.
    7. Newly
   
### Language translation
This feature translates the alert message to any available language.
Note: List of languages will be coming from the In-Telligent.

#### Android <a id='language-translation-android'></a>
The language translation feature will translate community notifications from the original language to any other supported language.
To implement this functionality, the following steps must be taken.
1. Notification: After authentication and getting buildings the following method is used to get complete notification data according to notification ID.
   ```java
   OpenAPI.getCompleteNotification(int notificationId,new Consumer< Notification>() {
    @Override
    public void accept(Notification notification) throws Exception {
     //Printing the Notification
     System.out.println(notification);
    }
   });
   ```
2. List of Languages: After getting building notifications, the following method is used to get a list of languages supported for Notification translation.
   ```java
   OpenAPI.getLanguages(new Consumer<NotificationLanguageResponse>() {
    @Override
    public void accept(NotificationLanguageResponse languageResponse) throws Exception {
     System.out.println(languageResponse.getLanguages());
    }
   });
   ```
3. Notification Translation: The method OpenAPI.getTranslation() is used to retrieve the translated message and the title of the message.
   Parameters: notification ID and target language.
   ```java
   OpenAPI.getTranslation(id,languageValue,new Consumer<TranslationResponse>() {
    @Override
    public void accept(TranslationResponse translationResponse) throws Exception {
     System.out.println(translationResponse.getBody());
     System.out.println(translationResponse.getTitle());
    }
   });
   ```
   
##### How to test <a id='test-language-translation-android'></a>
Testing can be done using the sample application that was shared as part of the build files.

#### iOS <a id='language-translation-ios'></a>
The language translation feature will translate community notifications from the original language to any other supported language.
To implement this functionality, the following steps must be taken.
1. Building Notifications: After authentication and getting buildings the following method is used to get notifications for buildings using “building id”.
   OpenAPI.getBuildingNotifications()
2. Notifications for building: After authentication and getting buildings the following method is used to get notifications for a single building according to building ID.
   OpenAPI.getNotificationsByBuilding(communityId)
3. Notifications: After authentication and getting buildings the following method is used to get complete notification data according to notification ID.
   OpenAPI.getCompleteNotification()
   ```swift
   OpenAPI.getCompleteNotification(notification_id){ notification in
    print(notification.description)
   } failure: { error in
    print(error?.localizedDescription ?? "")
   }
   ```
4. List of Languages: After getting building notifications, the following method is used to get a list of languages supported for Notification translation.
   INLanguageManager.getLanguages()
   ```swift
   INLanguageManager.getLanguages({ [weak self] (languages) in
    print(languages)
   }) { error in
        print(error?.localizedDescription ?? "")
   }
   ```
5. Notification Translation: The method INLanguageManager.getTranslation() is used to retrieve the translated message and the title of the message.
   Parameters: notification ID and target language.
   INLanguageManager.getTranslation()
   ```swift
   INLanguageManager.getTranslation(for: communityNotification, to: language, success: { translatedNotification in
    print(translatedNotification)
   }) { error in
        print(error?.localizedDescription ?? "")
   }
   ```
   
##### How to test <a id='test-language-translation-ios'></a>
Testing can be done using the sample application that was shared as part of the build files.

### Send alerts to all subscribers
A Community manager can initiate this API to send community alert to subscribers of his community. This will allow us to send different types of alerts (Regular, Personal, Ping, Critical, LSA) with or without attachments from the mobile app.
**Note: (To become a community manager for any given community, you need to contact admin)**
Admin can add a Community Manager in In-Telligent Web Portal. To register as a Manager, a valid email id / mobile number required. Once Admin adds a Community Manager for any given community, he will receive a link to set password. After setting the password, with that credentials Community Manager can login to the Mobile app.

#### Android <a id='send-alerts-android'></a>
To implement this functionality, the following steps must be taken.
1. Send Alert: After authentication with community manager credentials, you will be able to access the below API call with the necessary information.
   ```java
   OpenAPI.sendAlert(buildingId, title, message, notificationType, attachmentPaths, new Consumer<SuccessResponse>() {
    @Override
    public void accept(SuccessResponse successResponse) throws Exception{
     if (successResponse != null && successResponse.isSuccess()) {
      PrintLog.print(TAG, "Success");
     }else{
     PrintLog.print(TAG, "Error");
     }
    }
   });
   ```

   **Parameters:**
   **title –**
   Required - Yes
   **Type - String
   **message –**
   Required - Yes
   Type - String
   **buildingID –**
   Required - Yes
   Type – String
   **attachments –**
   Required – optional
   Type – Array of CustomImage Paths
   **notificationType –**
   Required – Yes
   Type – AlertType (OpenAPI library class)

##### How to test <a id='test-send-alerts-android'></a>
Testing can be done using the sample application that was shared as part of the build files. In Web portal we can verify it under community details in – View Sent Out Alerts

#### iOS <a id='send-alerts-ios'></a>
A Community manager can initiate this API to send community alert to subscribers of his community. This will allow us to send different types of alerts (Regular, Personal, Ping, Critical, LSA) with or without attachments from the mobile app.
To implement this functionality, the following steps must be taken.
**Note: (To become a community manager for any given community, you need to contact admin)**
Admin can add a Community Manager in In-Telligent Web Portal. To register as a Manager, a valid email id / mobile number required. Once Admin adds a Community Manager for any given community, he will receive a link to set password. After setting the password, with that credentials Community Manager can login to the Mobile app.
   1. Send Alert: After authentication with community manager credentials, you will be able to access the below API call with the necessary information.
      OpenAPI.sendAlert(title: alertTitle, message: alertMessage, building: buildingInformation, attachments: attachmentsInformation, notificationType: NotificationType)
      **Parameters:**
      **title –**
      Required - Yes
      Type - String
      **message –**
      Required - Yes
      Type - String
      **building –**
      Required - Yes
      Type – INBuilding (OpenAPI library class)
      **attachments –**
      Required – Yes
      Type – Array of CustomImage (OpenAPI library class)
      **notificationType –**
      Required – Yes
      Type – NotificationType (OpenAPI library class)
      ```swift
      OpenAPI.sendAlert(title: title_value, message: message_value, building: building, attachments: attachments, notificationType: notification_type, success: {
       // Handle success response
      }) { (error) in
       // Handle error response
      }
      ```

##### How to test <a id='test-send-alerts-ios'></a>
Testing can be done using the sample application that was shared as part of the build files. In Web portal we can verify it under community details in – View Sent Out Alerts
   
### Delivered alert status of an alert
The delivered alert status feature will inform the server about notification being delivered to the user. It will be done without developer's action on receiving an alert.

### Opened alert status
The opened alert status feature will inform the server about notification being read by the user.

#### Android <a id='alert-opened-android'></a>
To implement this functionality, the following steps must be taken.
   1. Call opened alert: After authentication, the following method is used to update server about alert opened status using notification ID.
      **Parameters:**
      **notificationId –**
      Required - Yes
      Type - int
      ```java
      OpenAPI.alertOpened(notificationId, new Consumer< SuccessResponse>() {
       @Override
       public void accept(SuccessResponse successResponse) throws Exception {
        if (successResponse != null && successResponse.isSuccess()) {
         // Opened status successfully sent to server
         } else if (successResponse != null) {
         PrintLog.print(TAG, "Opened alert  failure "+ successResponse.getError());
        } else {
         PrintLog.print(TAG, "Opened alert  failure ");
        }
       }
      });
      ```
#### iOS <a id='alert-opened-ios'></a>
To implement this functionality, the following steps must be taken 
1. Opened Alert: After authentication with community manager credentials, you will be able to access the below API call with the necessary information.
   OpenAPI.openedAlert(notificationId: notification_id)
   **Parameters:**
   **notificationId –**
   Required - Yes
   Type – Int
   ```swift
   OpenAPI.openedAlert(notificationId: aNotification.id, success: {
    //Handle success response
   }) { (error) in
    //Handle failure response
   }
   ```

### Delete an alert
The delete alert feature will enable a user to delete an alert.

#### Android <a id='delete-alert-android'></a>
To implement this functionality, the following steps must be taken.
   1. Call delete alert: After authentication and receiving at least 1 alert, the following method is used to delete an alert using notification ID.
      **Parameters:**
      **notificationId –**
      Required - Yes
      Type - int
      OpenAPI.deleteAlert ()
      ```java
      OpenAPI.deleteAlert (notificationId, new Consumer< SuccessResponse>() {
       @Override
       public void accept(SuccessResponse successResponse) throws Exception {
        if (successResponse != null && successResponse.isSuccess()) {
         // Alert successfully deleted
        } else if (successResponse != null) {
        PrintLog.print(TAG, "Delete alert  failure "+ successResponse.getError());
        } else {
         PrintLog.print(TAG, "Delete alert  failure ");
        }
       }
      });
      ```
   
#### iOS <a id='delete-alert-ios'></a>
To implement this functionality, the following steps must be taken
   1. Delete Alert: After authentication with community manager credentials, you will be able to access the below API call with the necessary information.
      OpenAPI.deleteAlert(notificationId: notification_id)
      **Parameters:**
      **notificationId –**
      Required - Yes
      Type – Int
      ```swift
      OpenAPI.deleteAlert(notificationId: notification.id, success: {
       //Handle success response
      }) { (error) in
       //Handle failure response
      }
      ```

## Integration with In-Telligent
Any partner application is required to integrate with In-Telligent systems to maintain user authentication and to send notifications from In-Telligent portals.
In order to integrate, the following details have to be shared with the In-Telligent development team. Once the In-Telligent development team receives the information, it may take up to two days to integrate with In-Telligent systems.
   1. Android<br>
      To send push notifications from the In-Telligent portal, your application’s firebase FCM server key has to be shared with the In-Telligent development team. This key can be retrieved from the Firebase console app created in Section 5.1 point 3.
         1. Locate the firebase project server key from the project created in Firebase console. (Project settings -> Cloud Messaging -> Server Key).
         2. Share the details with the In-Telligent development team.
   2. iOS<br>
      To send Apple PushKit notifications from the In-Telligent portal, your application’s certificates are required.
         1. Generate a P12 file in KEYS section in the Apple developer portal.
         2. Download the generated file.
         3. Share the file with the In-Telligent development team.

## References
   1. https://firebase.google.com/docs/cloud-messaging/android/client
   2. https://chrome.google.com/webstore/detail/restlet-client-rest-api-t/aejoelaoggembcahagimdiliamlcdmfm?hl=en
   3. https://console.firebase.google.com
   4. https://firebase.google.com/docs/cloud-messaging/server

   