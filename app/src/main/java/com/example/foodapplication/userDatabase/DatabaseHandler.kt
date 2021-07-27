package com.example.foodapplication.userDatabase

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

class DatabaseHandler(private var context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, 7) {

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
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun storeUserDetails(user: User):Boolean{

        val db = writableDatabase
        val values = ContentValues()

        values.put(COL_OTP, user.OTP)
        values.put(COL_NAME, user.name)
        values.put(COL_EMAIL, user.email)
        values.put(COL_PASSWORD, user.password)

        return if(userExists(user)){
            Toast.makeText(context, "This account already exists", Toast.LENGTH_SHORT).show()
            false
        } else{
            db.insert(TABLE_NAME, null, values)
            true
        }
        db.close()
        return true
    }

    fun checkUserDetails(user: User):Boolean{

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

    fun checkOTP(user: User):Boolean{

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

    fun resetPassword(user: User):Boolean{

        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_PASSWORD,user.password)
        val cursor = db.rawQuery("select $COL_PASSWORD from $TABLE_NAME where $COL_EMAIL= \'"+user.email+"\'",null,null)
        if (cursor.moveToNext()){
            val password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
            return if (userExists(user) && password != user.password) {
                db.update(TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(user.email))
                Toast.makeText(context, "Password successfully updated", Toast.LENGTH_SHORT).show()
                true
            }else if(password == user.password){
                Toast.makeText(context, "New password cannot be the same as old password", Toast.LENGTH_SHORT).show()
                false
            }
            else {
                Toast.makeText(context, "This account does not exist", Toast.LENGTH_SHORT).show()
                false
            }
        }
        return false
        db.close()
    }

    fun userExists(user: User):Boolean{

        val db = readableDatabase
        val query = "Select * from $TABLE_NAME where $COL_EMAIL= \'"+user.email+"\'"
        val cursor = db.rawQuery(query,null,null)

        return cursor.moveToFirst()
    }
}