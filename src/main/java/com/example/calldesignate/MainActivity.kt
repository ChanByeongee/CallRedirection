package com.example.calldesignate

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.provider.ContactsContract
import android.widget.EditText
import android.widget.Toast
import android.Manifest
import android.app.role.RoleManager
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


open class MainActivity : AppCompatActivity() {

    var dbHelper = DBHelper(this,"listdb.db",null,1)

    companion object{
        lateinit var numberlist : ArrayList<NumberData>

    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this.getApplicationContext(), "WelCome!!", Toast.LENGTH_SHORT).show()

        updateNumberList()

        //Register CallRedirectionService(class CallSelector)
        Role_req()


    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun Role_req(){
        val mRole = getSystemService(ROLE_SERVICE)as RoleManager

        val intent = mRole.createRequestRoleIntent(RoleManager.ROLE_CALL_REDIRECTION)


        this.startActivityForResult(intent,2)

    }

    fun updateNumberList(){
        numberlist=dbHelper.getAllList()
    }


    fun addNumber(view: View){
        /*
        Called by OnClick(addButton)
         */

        val number = findViewById<EditText>(R.id.editNum)

        val addnum = NumberData(number.text.toString())
        dbHelper.addNumber(addnum)

        Toast.makeText(this.getApplicationContext(),"Successfully Added", Toast.LENGTH_SHORT).show()

        number.editableText.clear()

        updateNumberList()
    }

    fun getList(view: View) {
        /*
         Called by OnClick(listButton)
         */
        val list_act = Intent(this,List::class.java)
        startActivity(list_act)
    }

    fun getContacts(view: View){
        /*
         Called by OnClick(contactButton)
         */
        if(CheckPermission("Contact")) {
            val intent = Intent(Intent.ACTION_PICK,ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            startActivityForResult(intent, 0)

        }
    }

    fun LoadContacts(data: Intent?){
        val cur = contentResolver.query(data?.data!!, arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        ), null, null, null)


        cur?.moveToFirst()
        val name = cur?.getString(1).toString()
        var phoneNumber = cur?.getString(2).toString()
        cur?.close()

        phoneNumber=phoneNumber.replace("tel:","")
        phoneNumber=phoneNumber.replace("-","")
        phoneNumber=phoneNumber.replace("(","")
        phoneNumber=phoneNumber.replace(")","")
        phoneNumber=phoneNumber.replace("%20","")
        phoneNumber=phoneNumber.replace(" ","")

        var addnum = NumberData(phoneNumber,name)
        dbHelper.addNumber(addnum)

        Toast.makeText(getApplicationContext(), "Sucessfully Added", Toast.LENGTH_SHORT).show()
        updateNumberList()
    }


    fun CheckPermission(reqPer : String) : Boolean {
        if (reqPer == "Contact") {
            val CheckContact = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            if (CheckContact == PackageManager.PERMISSION_GRANTED) {
                return true
            }
            else{
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)){
                    Toast.makeText(getApplicationContext(), "Read Contacts Permission Require", Toast.LENGTH_SHORT).show()
                }
                ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.READ_CONTACTS),0)
            }
        }

        //If you need permission of READ_PHONE_STATE or CALL_PHONE
        /*else if(reqPer=="PhoneState"){

            val CheckPhoneState = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)

            if(CheckPhoneState == PackageManager.PERMISSION_GRANTED) {
                return CheckPermission("CallPhone")
            }
            else{
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)){
                    Toast.makeText(getApplicationContext(), "Phone State Permission Require", Toast.LENGTH_SHORT).show()
                }
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE),1)
            }
        }
        else if(reqPer=="CallPhone"){
            val CheckCall = ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)
            if(CheckCall == PackageManager.PERMISSION_GRANTED){
                return true
            }
            else{
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){
                    Toast.makeText(getApplicationContext(), "Call Permission Require", Toast.LENGTH_SHORT).show()
                }
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),2)
            }
        }
        */
        return false
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //From getContact
        if(requestCode==0){
            if(resultCode== Activity.RESULT_OK) {
                LoadContacts(data)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {

            0 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Read Contacts Permission Granted, Please Retry ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplicationContext(), "Read Contacts Permission Denied, Please Retry", Toast.LENGTH_SHORT).show()
            }

            //If you need permission of READ_PHONE_STATE or CALL_PHONE
            /*
            1 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "PhoneState Permission Granted, Please Retry ", Toast.LENGTH_SHORT).show()
                CheckPermission("CallPhone")
            } else {
                Toast.makeText(getApplicationContext(), "PhoneState Permission Denied, Please Retry", Toast.LENGTH_SHORT).show()
            }

            2 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "CallPhone Permission Granted, Please Retry ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplicationContext(), "CallPhone  Permission Denied, Please Retry", Toast.LENGTH_SHORT).show()
            }
             */

        }
    }

}

