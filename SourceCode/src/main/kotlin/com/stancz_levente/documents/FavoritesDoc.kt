package com.stancz_levente.documents

import kotlinx.serialization.Serializable

// Serializable data class representing a Favorites for mongodb
@Serializable
data class FavoritesDoc(
    val id: String? = null,
    val userId: String? = null,
    val favorites: List<String>
)