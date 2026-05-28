package com.example.sao_joao_em_arcoverde.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.sao_joao_em_arcoverde.data.model.Schedule
import java.util.Calendar
import kotlin.math.abs

class FestivalNotificationScheduler(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(
        Context.ALARM_SERVICE
    ) as AlarmManager

    fun scheduleMainStageReminders(scheduleItems: List<Schedule>) {
        val mainStageItems = scheduleItems.filter {
            it.stageName.equals("Palco Principal", ignoreCase = true)
        }

        mainStageItems.forEach { schedule ->
            val reminderTimeMillis = calculateReminderTimeMillis(schedule) ?: return@forEach

            if (reminderTimeMillis <= System.currentTimeMillis()) {
                return@forEach
            }

            val pendingIntent = createPendingIntent(schedule)

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                        !alarmManager.canScheduleExactAlarms() -> {
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        reminderTimeMillis,
                        pendingIntent
                    )
                }

                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        reminderTimeMillis,
                        pendingIntent
                    )
                }

                else -> {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        reminderTimeMillis,
                        pendingIntent
                    )
                }
            }
        }
    }

    fun cancelMainStageReminders(scheduleItems: List<Schedule>) {
        val mainStageItems = scheduleItems.filter {
            it.stageName.equals("Palco Principal", ignoreCase = true)
        }

        mainStageItems.forEach { schedule ->
            alarmManager.cancel(createPendingIntent(schedule))
        }
    }

    private fun createPendingIntent(schedule: Schedule): PendingIntent {
        val intent = Intent(context, FestivalNotificationReceiver::class.java).apply {
            putExtra(
                FestivalNotificationConstants.EXTRA_ATTRACTION_NAME,
                schedule.artistName
            )
            putExtra(
                FestivalNotificationConstants.EXTRA_ATTRACTION_TIME,
                schedule.time
            )
            putExtra(
                FestivalNotificationConstants.EXTRA_STAGE_NAME,
                schedule.stageName
            )
        }

        return PendingIntent.getBroadcast(
            context,
            abs(schedule.id.hashCode()),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun calculateReminderTimeMillis(schedule: Schedule): Long? {
        val dateParts = schedule.date.split("-")
        val timeParts = schedule.time.split(":")

        if (dateParts.size != 3 || timeParts.size != 2) {
            return null
        }

        val year = dateParts[0].toIntOrNull() ?: return null
        val month = dateParts[1].toIntOrNull() ?: return null
        val day = dateParts[2].toIntOrNull() ?: return null
        val hour = timeParts[0].toIntOrNull() ?: return null
        val minute = timeParts[1].toIntOrNull() ?: return null

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (hour < 6) {
                add(Calendar.DAY_OF_MONTH, 1)
            }

            add(Calendar.MINUTE, -20)
        }

        return calendar.timeInMillis
    }

    fun sendTestNotification() {
        val intent = Intent(context, FestivalNotificationReceiver::class.java).apply {
            putExtra(
                FestivalNotificationConstants.EXTRA_ATTRACTION_NAME,
                "Alceu Valença"
            )
            putExtra(
                FestivalNotificationConstants.EXTRA_ATTRACTION_TIME,
                "23:00"
            )
            putExtra(
                FestivalNotificationConstants.EXTRA_STAGE_NAME,
                "Palco Principal"
            )
        }

        context.sendBroadcast(intent)
    }
}