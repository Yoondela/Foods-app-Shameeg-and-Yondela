package com.example.foodapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

const val DATABASE_NAME = "MyDB"
const val TABLE_NAME = "Users"
const val COL_NAME = "name"
const val COL_EMAIL = "email"
const val COL_PASSWORD = "password"
const val COL_ID = "id"
const val COL_OTP = "OTP"

class DatabaseHandler(private var context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME +" VARCHAR(20)," +
                COL_EMAIL +" VARCHAR(200),"+
                COL_PASSWORD +" VARCHAR(20),"+
                COL_OTP +" VARCHAR(4))"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun storeUserDetails(user:User){

        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_OTP,user.OTP)
        values.put(COL_NAME, user.name)
        values.put(COL_EMAIL,user.email)
        values.put(COL_PASSWORD,user.password)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun checkUserDetails(user:User):Boolean{

        val db = readableDatabase
        val query = "Select * from $TABLE_NAME"
        val cursor = db.rawQuery(query, null, null)

        while(cursor.moveToNext()){
            val email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
            val password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
            if(email == user.email && password == user.password){
                return true
            }
        }
        cursor.close()
        db.close()
        return false
    }

    fun checkOTP(user:User):Boolean{

        val db = readableDatabase
        val query = "Select * from $TABLE_NAME"
        val cursor = db.rawQuery(query, null, null)
        while(cursor.moveToNext()){
            val OTP = cursor.getString(cursor.getColumnIndex(COL_OTP))
            if(OTP == user.OTP){
                Toast.makeText(context, "OTP is correct", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        cursor.close()
        db.close()
        return false

    }
}