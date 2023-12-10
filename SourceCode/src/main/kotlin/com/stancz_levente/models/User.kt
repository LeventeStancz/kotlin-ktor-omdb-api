package com.stancz_levente.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

// Data class representing a User
data class User(
    @BsonId
    val id: Id<User>? = null,
    val username: String,
    val email: String,
    val password: String
)
