package com.example.jikananimeexplorer.sync

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object SyncScheduler {

    fun schedulePeriodicSync(context: Context) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request =
            PeriodicWorkRequestBuilder<AnimeSyncWorker>(
                24, TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "anime_sync_work",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun triggerOneTimeSync(context: Context) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request =
            OneTimeWorkRequestBuilder<AnimeSyncWorker>()
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context)
            .enqueue(request)
    }
}
