package com.benohayon.meallennium.framework.managers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.benohayon.meallennium.framework.models.ASK_CAMERA_PERMISSION_REQUEST

object PermissionManager {

    fun hasCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun doesNotHaveCameraPermission(context: Context): Boolean {
        return !hasCameraPermission(context)
    }

    fun requestCameraPermission(activity: AppCompatActivity) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), ASK_CAMERA_PERMISSION_REQUEST)
    }

    fun requestWriteStoragePermission(activity: AppCompatActivity) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), ASK_CAMERA_PERMISSION_REQUEST)
    }

    fun hasWriteStoragePermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    fun doesNotHaveWriteStoragePermission(context: Context): Boolean {
        return !hasWriteStoragePermission(context)
    }

    fun requestPermissions(activity: AppCompatActivity, permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity, permissions, ASK_CAMERA_PERMISSION_REQUEST)
    }
}