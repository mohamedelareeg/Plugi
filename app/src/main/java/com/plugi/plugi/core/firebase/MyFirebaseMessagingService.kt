package com.plugi.plugi.core.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson


class MyFirebaseMessagingService : FirebaseMessagingService() {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e("FIREN","--> ${Gson().toJson(remoteMessage.notification)}")
        Log.e("FIRE D","--> ${remoteMessage.data.toString()}")
        if (remoteMessage.data.isNotEmpty()) {
            val type = remoteMessage.data["type"]
            val title = remoteMessage.data["title"]
            val action_id = remoteMessage.data["action_id"]
            val message = remoteMessage.data["message"]
//            sendNotification(title,message,remoteMessage)

        }
    }

//    private fun sendNotification(title: String?, message: String?, remoteMessage: RemoteMessage) {
//
//        val intent = Intent(this, SplashActivity::class.java)
//        for ((key, value) in remoteMessage.data) {
//            intent.putExtra(key, value)
//        }
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//            PendingIntent.FLAG_ONE_SHOT)
//
//        val channelId = "CineChannel963"
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_logo_splash)
//            .setContentTitle(title?:"Compass")
//            .setContentText(message?:"New Notification")
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId,
//                "Compass Notification Channel",
//                NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
//    }

    override fun onNewToken(token: String) {
//        AuthorizationUseCases().saveFireBaseToken(token)

    }

    private fun scheduleJob() {

    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
     }

    private fun sendRegistrationToServer(token: String?) {

    }


    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}