package com.intelligent.openapidemo.utils
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.intelligent.openapidemo.R
import com.sca.seneca.lib.PrintLog

object LocationPermissionUtil {


    /**
     * Function to request permission from the user
     */

    enum class RequestPermissionType{
        PRECISE_LOCATION,
        BACKGROUND_LOCATION,
        LOCATION_PERMISSION,
        POST_NOTIFICATIONS
    }


    fun requestAccessFineLocationPermission(activity: Activity, requestId: Int) {

        val listPermissions = ArrayList(
            mutableListOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        // Only add background location to Android 10.
        // Android 11 permission prompt won't work if you include ACCESS_BACKGROUND_LOCATION.  Instead, for Android 11 a user must allow it in app settings
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) // Android 10
            listPermissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        ActivityCompat.requestPermissions(activity,
            listPermissions.toTypedArray(),
            requestId
        )
    }

    /**
     * This method checks the background location permission and returns the appropriate flag
     */
    fun isBackgroundLocationAccessGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) // Android 10 and above
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        else isLocationAccessGranted(context)
    }

    fun showPermissionRationale(permissionType: RequestPermissionType, context: Context){
        PrintLog.print("Tag", "************showPermissionRationale(isPreciseLocation: $permissionType)")
        val builder = AlertDialog.Builder(context)
        when(permissionType){
            RequestPermissionType.PRECISE_LOCATION -> {
                builder.setTitle(R.string.provide_fine_location_access)
                builder.setMessage(
                    context.getString(R.string.would_you_like_to_enable_your_location_all_the_time)
                        .replace("()", context.getString(R.string.app_name))
                )
            }
            RequestPermissionType.BACKGROUND_LOCATION -> {
                builder.setTitle(context.getString(R.string.need_background_location))
                builder.setMessage(
                    context.getString(R.string.would_you_like_to_enable_your_location_all_the_time)
                        .replace("()", context.getString(R.string.app_name))
                )
            }
            RequestPermissionType.LOCATION_PERMISSION -> {
                builder.setTitle(context.getString(R.string.provide_location_access))
                builder.setMessage(
                    context.getString(R.string.would_you_like_to_enable_your_location_all_the_time)
                        .replace("()", context.getString(R.string.app_name))
                )
            }
            RequestPermissionType.POST_NOTIFICATIONS -> {
                builder.setTitle(context.getString(R.string.provide_notification_permissions))
                builder.setMessage(context.getString(R.string.enable_notification_permission))
            }
        }

        builder.setCancelable(false)
            .setPositiveButton(R.string.settings) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_AUTO_REVOKE_PERMISSIONS, Uri.parse(
                                "package:${context.packageName}"
                            )
                        )
                    )
                }else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                }
            }
            .setNegativeButton(R.string.ask_later) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    /**
     * Function to check if the location permissions are granted or not
     */


    fun isLocationAccessGranted(context: Context): Boolean {
        return isAccessCoarseLocationGranted(context) || isAccessFineLocationGranted(context)
    }

    /**
     * Function to check if the location permissions are granted or not
     */
    fun isAccessCoarseLocationGranted(context: Context): Boolean {
        var permissionGranted = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                permissionGranted = true
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                permissionGranted = true
            }
        }
        return permissionGranted
    }
    /**
     * Checks if Fine and Coarse locations are granted. If both are granted, then only it will return true. Else it will return False.
     * This method works for Android 9, 10, 11 and 12 as on 8th July 2022.
     */
    fun isAccessFineLocationGranted(context: Context): Boolean {
        return (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    /**
     * Function to check if location of the device is enabled or not
     */
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    /**
     * Function to show the "enable GPS" Dialog box
     */
    fun showGPSNotEnabledDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context)
        val dialog = builder.create()
        builder.setMessage(context.getString(R.string.message_gps_disabled))
            .setCancelable(false)
            .setNegativeButton(context.getString(R.string.cancel), null)
            .setPositiveButton(context.getString(R.string.yes)) { _, _->
                dialog.dismiss()
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .show()
    }
}