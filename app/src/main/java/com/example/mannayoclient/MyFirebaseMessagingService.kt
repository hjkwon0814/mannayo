package com.example.mannayoclient

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("FCM Log", "Refreshed token: " + token)
    }

    override fun onMessageReceived(message: RemoteMessage) {

    }
}