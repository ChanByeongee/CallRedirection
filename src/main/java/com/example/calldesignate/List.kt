package com.example.calldesignate
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class List<T> : AppCompatActivity(),OnBtnClick {

    /*
        implanted Interface : OnBtnClick
     */
    lateinit var dbHelper : DBHelper


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
        MainActivity.numberlist = dbHelper.getAllList()
        val list_adapter = ListAdapter(this, MainActivity.numberlist,this)

        val numberListView = findViewById<ListView>(R.id.numberListView)

        numberListView.adapter = list_adapter


    }
    fun req_ModifyList(Number: NumberData){
        /*
            Called by OnClick()
         */

        //Load Modify window
        var intent = Intent(this, ModifyPopUp::class.java)
        intent.putExtra("Finder",Number.get_name())
        startActivityForResult(intent,1)

    }

    fun ModifyList(Number: NumberData,finder:String){
        dbHelper.updateNumber(Number,finder)
        Toast.makeText(this.getApplicationContext(),"Successfully Modified", Toast.LENGTH_SHORT).show()

        load_data()
    }

    fun DeleteList(Number: NumberData){
        dbHelper.deleteNumber(Number)
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

                var re_name = data?.getStringExtra("Name")
                var re_num = data?.getStringExtra("Number")
                var finder = data?.getStringExtra("Finder")


                var add = NumberData(re_num.toString(),re_name.toString())
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

