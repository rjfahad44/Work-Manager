package com.ft.ltd.work_manager.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ft.ltd.work_manager.R
import com.ft.ltd.work_manager.presentation.activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Timer

class Workers(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val NOTIFICATION_ID = 1
    }

    override fun doWork(): Result {
        Log.d("success", "doWork: Success function called")
        showNotification()

        return try {
            Result.success()
        } catch (error: Exception) {
//            if (runAttemptCount < 10) Result.retry() else Result.failure()
            Result.failure()
        }
    }

    private fun showNotification() {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, FLAG_MUTABLE)

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("New Task")
            .setContentText("Subscribe on the channel")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Channel Name"
            val channelDescription = "Channel Description"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID, channelName, channelImportance).apply {
                description = channelDescription
            }

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, notification.build())
        }
    }
}