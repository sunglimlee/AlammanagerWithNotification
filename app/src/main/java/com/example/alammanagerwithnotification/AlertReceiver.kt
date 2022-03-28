package com.example.alammanagerwithnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class AlertReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val notificationHelper = NotificationHelper(p0)
        val nb : NotificationCompat.Builder = notificationHelper.getChannel1Notification("Alarm", "알람시간입니다. 이제 일어나야죠.")
        notificationHelper.getManager().notify(1, nb.build())
    }

}