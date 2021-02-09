package com.example.calldesignate

import android.net.Uri
import android.os.Build
import android.telecom.CallRedirectionService
import android.telecom.PhoneAccountHandle
import androidx.annotation.RequiresApi


@RequiresApi(Build.VERSION_CODES.Q)
class CallSelector : CallRedirectionService(){

    var numberlist = MainService.numberlist

    override fun onPlaceCall(handle: Uri, initialPhoneAccount: PhoneAccountHandle, allowInteractiveResponse: Boolean) {

        var phoneNumber = handle.toString()

        //Modify_PHONE_NUMBER
        phoneNumber=phoneNumber.replace("tel:","")
        phoneNumber=phoneNumber.replace("-","")
        phoneNumber=phoneNumber.replace("(","")
        phoneNumber=phoneNumber.replace(")","")
        phoneNumber=phoneNumber.replace("%20","")

        //Load numberlist
        val mList = numberlist

        var flag : Boolean = false
        for( i in 0 .. mList.size-1){
            if(mList[i].get_number()==phoneNumber){
                flag=true
                break
            }
        }

        if(flag) {
            //Allow to call
            placeCallUnmodified()
        }
        else{
            //Deny to call
            cancelCall()
        }
    }

}