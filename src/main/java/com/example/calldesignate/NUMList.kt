package com.example.calldesignate
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class NUMLists : AppCompatActivity(),OnBtnClick {

    /*
        implemented Interface : OnBtnClick
     */
    lateinit var dbHelper : DBHelper

    lateinit var numberlist : ArrayList<NumberData>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_window)

        dbHelper = DBHelper(this,"listdb.db",null,1)

        load_data()
    }
    fun load_data(){
        /*
            Update numberlist to show

         */
        numberlist = dbHelper.getAllList()
        val list_adapter = ListAdapter(this, numberlist,this)

        val numberListView = findViewById<ListView>(R.id.numberListView)

        numberListView.adapter = list_adapter


    }
    fun req_ModifyList(Number: NumberData){
        /*
            Called by OnClick()
         */

        //Load Modify window
        val intent = Intent(this, ModifyPopUp::class.java)
        intent.putExtra("Finder",Number.get_name())
        startActivityForResult(intent,1)

    }

    fun reqUpdateService(){
        val intent = Intent(this, MainService::class.java)
        intent.putExtra("Request","Update")
        startService(intent)
    }


    fun ModifyList(Number: NumberData,finder:String){
        dbHelper.updateNumber(Number,finder)

        reqUpdateService()

        Toast.makeText(this.getApplicationContext(),"Successfully Modified", Toast.LENGTH_SHORT).show()

        load_data()
    }

    fun DeleteList(Number: NumberData){
        dbHelper.deleteNumber(Number)

        reqUpdateService()

        Toast.makeText(this.getApplicationContext(),"Successfully Deleted", Toast.LENGTH_SHORT).show()

        load_data()
    }


    override fun onClick(Type: Int,Number: NumberData) {

        //Interface
        if(Type==1){
            req_ModifyList(Number)
        }
        else if(Type==2){
            DeleteList(Number)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){

                val re_name = data?.getStringExtra("Name")
                val re_num = data?.getStringExtra("Number")
                val finder = data?.getStringExtra("Finder")


                val add = NumberData(re_num.toString(),re_name.toString())
                ModifyList(add,finder.toString())

            }
            else {
                Toast.makeText(this.getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show()

            }
        }

    }
}

interface OnBtnClick{
    fun onClick(Type: Int, Number:NumberData)
}

