package com.intelligent.openapidemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.service.CommunityUpdateWorker
import com.sca.in_telligent.openapi.util.CommunityTypeAllowed
import com.sca.seneca.lib.PrintLog

class MainActivity : AppCompatActivity() {



    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val Notification_PERMISSION_CODE = 10

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        checkLocationPermission()
        OpenAPI.checkDNDPermission(this)
        checkNotificationPermissions()





    }

    private fun checkLocationPermission() {
        if ((OpenAPI.getCommunityTypeAllowed(this) == CommunityTypeAllowed.PHYSICAL
                    || OpenAPI.getCommunityTypeAllowed(this) == CommunityTypeAllowed.ALL)
            && OpenAPI.isAutoSubscriptionEnabled(this)) {
            PrintLog.print("Tag", "************checkLocationPermission()")
            if (LocationPermissionUtil.isLocationAccessGranted(this) && SharedPreferencesHelper.getLogedIn(
                    this
                )
            ) { // works for Android 9 and above
                PrintLog.print(
                    "Tag",
                    "MainActivity************Location access granted and user logged in "
                )
                CommunityUpdateWorker.startWork(this, null)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // An+droid 11 and above
                if (LocationPermissionUtil.isAccessFineLocationGranted(this)) {
                    PrintLog.print("Tag", "************AccessFineLocationGranted")
                    if (!LocationPermissionUtil.isLocationEnabled(this)) {
                        LocationPermissionUtil.showGPSNotEnabledDialog(this)
                    } else {
                        if (!LocationPermissionUtil.isBackgroundLocationAccessGranted(this)) {
                            LocationPermissionUtil.showPermissionRationale(
                                LocationPermissionUtil.RequestPermissionType.BACKGROUND_LOCATION,
                                this
                            )
                            //showBackgroundPermissionRationale()
                        }
                    }
                } else if (LocationPermissionUtil.isAccessCoarseLocationGranted(this)) {
                    PrintLog.print(
                        "Tag",
                        "************Course location granted. But FineLocation Not Granted"
                    )
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        LocationPermissionUtil.showPermissionRationale(
                            LocationPermissionUtil.RequestPermissionType.PRECISE_LOCATION,
                            this
                        )
                    } else {
                        LocationPermissionUtil.requestAccessFineLocationPermission(
                            this,
                            LOCATION_PERMISSION_REQUEST_CODE
                        )
                    }
                } else {
                    PrintLog.print("Tag", "************No location is granted")
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) || shouldShowRequestPermissionRationale(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                        LocationPermissionUtil.showPermissionRationale(
                            LocationPermissionUtil.RequestPermissionType.LOCATION_PERMISSION,
                            this
                        )
                    else
                        LocationPermissionUtil.requestAccessFineLocationPermission(
                            this,
                            LOCATION_PERMISSION_REQUEST_CODE
                        )
                }
            } else { // For Android 10 (Only)
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    LocationPermissionUtil.showPermissionRationale(
                        LocationPermissionUtil.RequestPermissionType.BACKGROUND_LOCATION,
                        this
                    )
                } else {
                    LocationPermissionUtil.requestAccessFineLocationPermission(
                        this,
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        PrintLog.print("Tag", "************onRequestPermissionsResult()")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if(LocationPermissionUtil.isLocationAccessGranted(this) && SharedPreferencesHelper.getLogedIn(this)){
                    PrintLog.print("Tag", "************Location Permission granted and user logged in")
                    CommunityUpdateWorker.startWork(this, null)
                }
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Fine location granted
                    PrintLog.print("Tag", "************Fine Location Permission granted")
                    if (!LocationPermissionUtil.isLocationEnabled(this)) {
                        LocationPermissionUtil.showGPSNotEnabledDialog(this)
                    }
                    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q && !(LocationPermissionUtil.isBackgroundLocationAccessGranted(
                            this
                        ))
                    ) {
                        LocationPermissionUtil.showPermissionRationale(
                            LocationPermissionUtil.RequestPermissionType.BACKGROUND_LOCATION,
                            this
                        )
                    }
                } else if (grantResults.isNotEmpty() && grantResults[1] == PackageManager.PERMISSION_GRANTED) { // Coarse location granted
                    PrintLog.print("Tag", "************Location Permission not granted")
                    LocationPermissionUtil.showPermissionRationale(LocationPermissionUtil.RequestPermissionType.PRECISE_LOCATION,this)
                    // Show only Precise location request
                } else { // If the user has not provided any location permission
                    LocationPermissionUtil.showPermissionRationale(LocationPermissionUtil.RequestPermissionType.LOCATION_PERMISSION,this)
                    // Show location permission request
                }
            }
            Notification_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_DENIED
                ) {
                    val showRationale = shouldShowRequestPermissionRationale(permissions[0])
                    if (!showRationale) {
                        LocationPermissionUtil.showPermissionRationale(LocationPermissionUtil.RequestPermissionType.POST_NOTIFICATIONS,this)
                    }
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                Notification_PERMISSION_CODE
            )
        }
    }

    }










