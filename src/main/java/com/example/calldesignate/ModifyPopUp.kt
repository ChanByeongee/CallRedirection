package com.example.calldesignate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.EditText

class ModifyPopUp : Activity() {

    lateinit var finder : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.modify_popup)

        var intent : Intent = getIntent()
        finder = intent.getStringExtra("Finder").toString()

    }

    fun OkClose(view : View){
        //Touch OK button
        val name = findViewById<EditText>(R.id.editName)
        val number = findViewById<EditText>(R.id.editNumber)

        var intent = Intent()
        intent.putExtra("Name",name.text.toString())
        intent.putExtra("Number",number.text.toString())
        intent.putExtra("Finder",finder)

        setResult(RESULT_OK,intent)

        finish()

    }

    fun CancelClose(view: View){
        //Touch Cancel button
        var intent = Intent()
        setResult(RESULT_CANCELED,intent)

        finish()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //Do not touch OutSide of window
        if(event?.action==MotionEvent.ACTION_OUTSIDE){
            return false
        }
        return true
    }

}