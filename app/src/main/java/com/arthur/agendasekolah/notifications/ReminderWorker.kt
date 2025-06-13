package com.arthur.agendasekolah.notifications

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.arthur.agendasekolah.R

class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        val title = inputData.getString("title")
        val description = inputData.getString("description")

        showNotification(title ?: "Ujian/Tugas", description ?: "Ingatkan saya tentang ujian/tugas")

        return Result.success()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showNotification(title: String, description: String) {
        val notification = NotificationCompat.Builder(applicationContext, "school_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(1, notification)
    }
}
