package com.example.calldesignate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ListAdapter(val context : Context, val listArry : ArrayList<NumberData>,val Listener : OnBtnClick) : BaseAdapter(),View.OnClickListener {

    /*
        Implanted Interface : Listener
     */

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.list_view,null)

        val Name = view.findViewById<TextView>(R.id.Name)
        val Number = view.findViewById<TextView>(R.id.Number)
        val Modify = view.findViewById<Button>(R.id.Modify)
        val Delete = view.findViewById<Button>(R.id.Delete)

        val number = listArry[position]

        //Tag real number of list
        Modify.tag=position
        Delete.tag=position

        //Button OnClick
        Modify.setOnClickListener(this)
        Delete.setOnClickListener(this)

        Name.text = number.get_name()
        Number.text = number.get_number()


        return view

    }
    override fun onClick(v: View?) {

        when(v?.id){
            R.id.Modify -> {
                modifyNum(v?.tag as Int)
            }
            R.id.Delete -> {
                deleteNum(v?.tag as Int)
            }
        }


    }

    override fun getItem(position: Int): Any {
        return listArry[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listArry.size
    }

    fun modifyNum(pos:Int){




        val curnumber = listArry[pos]

        //Listener is Interface
        Listener.onClick(1,curnumber)
    }

    fun deleteNum(pos:Int){

        val curnumber = listArry[pos]

        //Listener is Interface
        Listener.onClick(2,curnumber)
    }

}
