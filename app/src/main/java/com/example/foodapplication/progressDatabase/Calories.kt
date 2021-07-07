package com.example.foodapplication.progressDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calories_table")
data class Calories(
    @PrimaryKey
    val uid:String,
    val calories:Int,
    val date:String
)