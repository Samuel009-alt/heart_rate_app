package com.example.heart_rate_app.data.models

data class UserData (
    // Data class to hold user information
    val uid: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val age: Int? = null,
    val gender: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val profileImageUrl: String? = null
){
    constructor() : this(
        null,
        null,
        null,
        null,
        null,

        null,
        null,
        null)
}
