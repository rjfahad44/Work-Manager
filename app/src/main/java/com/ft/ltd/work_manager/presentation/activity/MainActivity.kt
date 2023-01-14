package com.ft.ltd.work_manager.presentation.activity

import android.os.Bundle
import androidx.work.*
import com.ft.ltd.work_manager.databinding.ActivityMainBinding
import com.ft.ltd.work_manager.presentation.base.BaseActivity
import com.ft.ltd.work_manager.worker.Workers

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun setBinding(): ActivityMainBinding =  ActivityMainBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeWorkManager()
    }

    private fun initializeWorkManager() {
        oneTimeWork()
        periodicWork()
    }

    private fun periodicWork() {
        TODO("Not yet implemented")
    }

    private fun oneTimeWork() {
        val oneTimeWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<Workers>().build()
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)

        val workManager = WorkManager.getInstance(this)
        val myWorker = OneTimeWorkRequestBuilder<Workers>().build()
        workManager.enqueueUniqueWork("worker1", ExistingWorkPolicy.KEEP, myWorker)
    }

}