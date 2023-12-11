package com.example.service_mymusicapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class MyApplication : Application(){
    val CHANNEL_ID : String = "channel_service_example"
    override fun onCreate() {
        super.onCreate()
        createChannelNotification()// từ android26 phải tạo channel id
    }

    private fun createChannelNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel : NotificationChannel = NotificationChannel(CHANNEL_ID,"Channel service app",NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null, null)
            val manager : NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        private var instance: MyApplication? = null

        fun getInstance(): MyApplication {
            if(instance == null){
                instance = MyApplication()
            }
            return instance!!
        }
    }
}