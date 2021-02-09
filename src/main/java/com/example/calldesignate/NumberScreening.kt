package com.example.calldesignate


import android.os.Build

import android.telecom.*
import androidx.annotation.RequiresApi
import android.telecom.Call


@RequiresApi(api = Build.VERSION_CODES.Q)
class NumberScreening :CallScreeningService() {


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onScreenCall(callDetails: Call.Details) {

        /*
            To Screen number + Respond to Call

         */


    }
    fun buildResponse():CallResponse{
        return CallResponse.Builder()
                .setRejectCall(true)
                .setDisallowCall(true)
                .setSkipNotification(true)
                .setSkipCallLog(true)
                .build()
    }



}
