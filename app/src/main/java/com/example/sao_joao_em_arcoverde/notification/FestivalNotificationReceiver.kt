package com.example.sao_joao_em_arcoverde.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.sao_joao_em_arcoverde.R
import kotlin.math.abs

class FestivalNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        createNotificationChannel(context)

        val attractionName = intent.getStringExtra(
            FestivalNotificationConstants.EXTRA_ATTRACTION_NAME
        ) ?: "Atração"

        val attractionTime = intent.getStringExtra(
            FestivalNotificationConstants.EXTRA_ATTRACTION_TIME
        ) ?: ""

        val stageName = intent.getStringExtra(
            FestivalNotificationConstants.EXTRA_STAGE_NAME
        ) ?: "Palco Principal"

        val openAppIntent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)
            ?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }

        val contentIntent = PendingIntent.getActivity(
            context,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            context,
            FestivalNotificationConstants.CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Faltam 20 minutos!")
            .setContentText("$attractionName sobe no $stageName às $attractionTime.")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Faltam 20 minutos para $attractionName subir no $stageName. Horário previsto: $attractionTime.")
            )
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        val notificationId = abs(attractionName.hashCode() + attractionTime.hashCode())

        notificationManager.notify(notificationId, notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            FestivalNotificationConstants.CHANNEL_ID,
            FestivalNotificationConstants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = FestivalNotificationConstants.CHANNEL_DESCRIPTION
        }

        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}