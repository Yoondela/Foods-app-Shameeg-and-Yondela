package com.example.foodapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "Users"
val COL_NAME = "name"
val COL_EMAIL = "email"
val COL_PASSWORD = "password"
val COL_ID = "id"

class DatabaseHandler(var context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME +" VARCHAR(20)," +
                COL_EMAIL +" VARCHAR(200),"+
                COL_PASSWORD +" VARCHAR(20))"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun storeUserDetails(user:User){

        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, user.name)
        values.put(COL_EMAIL,user.email)
        values.put(COL_PASSWORD,user.password)

        var result = db.insert(TABLE_NAME, null, values)
        if (result == (-1).toLong()){
            Toast.makeText(context,"failed", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"success", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun checkUserDetails(user:User):Boolean{

        val db = readableDatabase
        val query = "Select * from " + TABLE_NAME
        val cursor = db.rawQuery(query, null, null)

        while(cursor.moveToNext()){
            var email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
            var password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
            if(email == user.email && password == user.password){
                Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        cursor.close()
        db.close()
        return false
    }
}