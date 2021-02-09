package com.example.calldesignate
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
) : SQLiteOpenHelper(context, name, factory, version) {


    override fun onCreate(db: SQLiteDatabase) {
        val sql : String = "CREATE TABLE if not exists ListTable (" +
                "_id INTEGER primary key autoincrement," +"Name TEXT, "+
                "Number TEXT);"

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists ListTable"

        db.execSQL(sql)
        onCreate(db)
    }

    fun getAllList():ArrayList<NumberData>{
        val lstNumbers = ArrayList<NumberData>()
        val select = "SELECT * FROM ListTable"
        val db = this.writableDatabase
        val cur = db.rawQuery(select,null)
        if(cur.moveToFirst())
        {
            do{
                val number = NumberData()
                number.set_name(cur.getString(cur.getColumnIndex("Name")))
                number.set_number(cur.getString(cur.getColumnIndex("Number")))

                lstNumbers.add(number)
            }while (cur.moveToNext())
        }
        cur.close()
        db.close()
        return lstNumbers
    }

    fun addNumber(number : NumberData){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("Name", number.get_name())
        values.put("Number",number.get_number())

        db.insert("ListTable",null,values)
        db.close()
    }

    fun updateNumber(number: NumberData, find : String):Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("Name", number.get_name())
        values.put("Number",number.get_number())

        return db.update("ListTable",values,"Name=?", arrayOf(find))
    }

    fun deleteNumber(number: NumberData){
        val db = this.writableDatabase

        db.delete("ListTable","Name Like ?", arrayOf(number.get_name()))

        db.close()
    }
}