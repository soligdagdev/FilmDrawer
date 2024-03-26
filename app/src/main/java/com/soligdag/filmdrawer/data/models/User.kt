package com.soligdag.filmdrawer.data.models

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

class User : Serializable {
    var id: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var isEmailVerified: Boolean? = false
    var wishListVersion : Int = 0
//    @ServerTimestamp
//    var createdDate : Timestamp? = null
//    var username = ""
//    var genres = ArrayList<String>()

    internal constructor() {
        Log.d("","")
    }
    internal constructor(uid : String,email : String) {
        this.id = uid
        this.email = email
    }
    constructor(firebaseUser : FirebaseUser) {
        this.id = firebaseUser.uid
        this.firstName = firebaseUser.displayName
        this.email = firebaseUser.email
        this.isEmailVerified = firebaseUser.isEmailVerified
    }
}