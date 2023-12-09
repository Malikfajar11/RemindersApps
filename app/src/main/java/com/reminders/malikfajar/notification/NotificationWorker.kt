package com.reminders.malikfajar.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.reminders.malikfajar.data.Task
import com.reminders.malikfajar.ui.detail.DetailTaskActivity
import com.reminders.malikfajar.R
import com.reminders.malikfajar.data.TaskRepository
import com.reminders.malikfajar.utils.DateConverter
import com.reminders.malikfajar.utils.NOTIFICATION_CHANNEL_ID
import com.reminders.malikfajar.utils.TASK_ID

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val channelName = inputData.getString(NOTIFICATION_CHANNEL_ID)

    private fun getPendingIntent(task: Task): PendingIntent? {
        val intent = Intent(applicationContext, DetailTaskActivity::class.java).apply {
            putExtra(TASK_ID, task.id)
        }
        return TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            } else {
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        }
    }

    override fun doWork(): Result {

        val taskRepository = TaskRepository.getInstance(applicationContext)
        val nearestTask = taskRepository.getNearestActiveTask()
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val itemNotification = NotificationCompat.Builder(applicationContext, channelName ?: "Task Reminder")
            .setContentTitle(nearestTask.title)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentText(String.format(applicationContext.getString(R.string.notify_content), DateConverter.convertMillisToString(nearestTask.dueDateMillis)))
            .setAutoCancel(true)
            .setContentIntent(getPendingIntent(nearestTask))
            .build()

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

           val importance = NotificationManager.IMPORTANCE_DEFAULT
           val channel = NotificationChannel(channelName, nearestTask.title, importance).apply { description = nearestTask.description }

           notificationManager.createNotificationChannel(channel)
       }

        notificationManager.notify(nearestTask.id, itemNotification)

        return Result.success()
    }

}
