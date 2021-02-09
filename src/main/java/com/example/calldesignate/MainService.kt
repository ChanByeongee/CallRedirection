package com.example.calldesignate


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.core.app.NotificationCompat

class MainService : Service() {
    var dbHelper = DBHelper(this,"listdb.db",null,1)
    companion object {
        lateinit var numberlist: ArrayList<NumberData>
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate() {
        super.onCreate()

        val notiManage = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notiChannel = NotificationChannel("Call_MainService_id","CallDesignated",NotificationManager.IMPORTANCE_DEFAULT)
        notiManage.createNotificationChannel(notiChannel)

        val noti = NotificationCompat.Builder(this,"Call_MainService_id")
        noti.setSmallIcon(R.mipmap.ic_launcher)
        noti.setContentTitle("CallDesignated")
        noti.setContentText("Service Working...")

        update_numberlist()
        startForeground(1,noti.build())

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(intent == null){
            update_numberlist()
            return START_STICKY
        }
        else{
            val reqCode = intent.getStringExtra("Request").toString()
            if(reqCode == "Update"){
                update_numberlist()
            }
            else if(reqCode == "Get"){
               /*
                    Not implemented
                */
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    fun update_numberlist(){
        numberlist = dbHelper.getAllList()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


}