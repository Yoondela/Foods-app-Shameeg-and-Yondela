package com.example.foodapplication.progressDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Calories::class],version = 1,exportSchema = false)
abstract class CaloriesDatabase: RoomDatabase() {

    abstract fun userIntakeDao():CaloriesDao

    companion object{
        @Volatile
        private var INSTANCE:CaloriesDatabase? = null

        fun getIntakeDatabase(context: Context):CaloriesDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CaloriesDatabase::class.java,
                    "calories_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}