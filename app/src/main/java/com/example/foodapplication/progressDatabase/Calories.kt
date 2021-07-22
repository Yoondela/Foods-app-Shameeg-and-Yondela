package com.example.foodapplication.progressDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calories_table")
data class Calories(
    @PrimaryKey(autoGenerate = true)
    val uid:Int,
    val userEmail:String,
    var calories:Int,
    val date:Float
)
