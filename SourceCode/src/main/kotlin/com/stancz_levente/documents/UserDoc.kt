package com.stancz_levente.documents

import kotlinx.serialization.Serializable

// Serializable data class representing a User for mongodb
@Serializable
data class UserDoc(
    val id: String? = null,
    val username: String,
    val  email: String,
    val password: String
)