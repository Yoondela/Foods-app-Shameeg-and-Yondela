package com.example.foodapplication.exercises

import com.google.gson.JsonObject

class ExercisesInfoItems {
    var tag_id: String
    var user_input: String
    var duration_min: String
    var met: String
    var nf_calories: Double
    var photo: JsonObject
    var compendium_code: String
    var name: String
    var description: String
    var benefits: String

    constructor(tag_id: String, user_input: String, duration_min: String,
                met: String, nf_calories: Double, photo: JsonObject,
                compendium_code:String, name:String, description: String,
                benefits: String)

    {
        this.tag_id = tag_id
        this.user_input = user_input
        this.duration_min = duration_min
        this.met = met
        this.nf_calories = nf_calories
        this.photo = photo
        this.compendium_code = compendium_code
        this.name = name
        this.description = description
        this.benefits = benefits

    }
}

class ExercisesInfo{
    var exercises: List<JsonObject>

    constructor(exercises: List<JsonObject>) {
        this.exercises = exercises
    }
}