package com.ft.ltd.work_manager.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.ft.ltd.work_manager.databinding.ActivityMainBinding
import com.ft.ltd.work_manager.presentation.base.BaseActivity
import com.ft.ltd.work_manager.worker.Workers
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun setBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onViewReady(savedInstanceState: Bundle?) {
        initializeWorkManager()
    }

    private fun initializeWorkManager() {
        oneTimeWork()
        //periodicWork()
    }

    private fun periodicWork() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()
        //minimum interval is 15-min, just wait 15 min
        val myRequest = PeriodicWorkRequest.Builder(
            Workers::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "work_id",
                ExistingPeriodicWorkPolicy.KEEP,
                myRequest
            )
    }

    private fun oneTimeWork() {
        val workManager = WorkManager.getInstance(this)
        val myWorker = OneTimeWorkRequestBuilder<Workers>().build()
        workManager.enqueueUniqueWork("worker1", ExistingWorkPolicy.REPLACE, myWorker)

        workManager.getWorkInfoByIdLiveData(myWorker.id).observe(this) {
            binding.tv.text = it.runAttemptCount.toString()
            Log.d("WORKERS", "id = ${it.id}")
            Log.d("WORKERS", "state.name = ${it.state.name}")
            Log.d("WORKERS", "state.isFinished = ${it.state.isFinished}")
            Log.d("WORKERS", "progress = ${it.progress}")
            Log.d("WORKERS", "tags = ${it.tags}")
            Log.d("WORKERS", "runAttemptCount = ${it.runAttemptCount}")
        }

        //cancel worker by UUId
        /*
        val workManager = WorkManager.getInstance(context)
        val work = workManager.getWorkInfosByTag(MyWorker.TAG).get()
        val uuid = work[0].id
        workManager.cancelWorkById(uuid)
        */
    }
}