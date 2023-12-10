package com.stancz_levente.converters

import com.stancz_levente.documents.UserDoc
import com.stancz_levente.models.User

// Extension functions to convert between User and UserDoc
fun User.toDocument(): UserDoc =
    UserDoc(
        id = this.id.toString(),
        username = this.username,
        email = this.email,
        password = this.password
    )

fun UserDoc.toUser(): User =
    User(
        username = this.username,
        email = this.email,
        password = this.password
    )