package com.stancz_levente.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

// Data class representing user favorites
data class Favorites(
    @BsonId
    val id: Id<Favorites>? = null,
    @BsonId
    val userId: Id<User>? = null,
    val favorites: List<String>
)