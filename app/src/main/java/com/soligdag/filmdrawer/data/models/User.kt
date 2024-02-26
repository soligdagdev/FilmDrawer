package com.soligdag.filmdrawer.data.models

import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

class User : Serializable {
    var uid: String? = null
    var name: String? = null
    var email: String? = null
    var isEmailVerified: Boolean? = false
//    @ServerTimestamp
//    var createdDate : Timestamp? = null
//    var username = ""
//    var genres = ArrayList<String>()

    internal constructor() {

    }
    internal constructor(uid : String,email : String) {
        this.uid = uid
        this.email = email
    }
    constructor(firebaseUser : FirebaseUser) {
        this.uid = firebaseUser.uid
        this.name = firebaseUser.displayName
        this.email = firebaseUser.email
        this.isEmailVerified = firebaseUser.isEmailVerified
    }
}