package com.example.foodapplication.userDatabase

class User {

    var id = 0
    var name = "name"
    var email = "email"
    var password = "password"
    var OTP = ""

    constructor(name:String,email:String,password:String,OTP: String){
        this.name = name
        this.email = email
        this.password = password
        this.OTP = OTP
    }

    constructor(email:String,password:String){
        this.email = email
        this.password = password
    }

    constructor(OTP: String){
        this.OTP = OTP
    }
}