package com.stancz_levente.converters

import com.stancz_levente.documents.FavoritesDoc
import com.stancz_levente.models.Favorites
import org.litote.kmongo.toId

// Extension functions to convert between Favorites and FavoritesDoc
fun Favorites.toDocument(): FavoritesDoc =
    FavoritesDoc(
        id = this.id.toString(),
        userId = this.userId.toString(),
        favorites = this.favorites
    )

fun FavoritesDoc.toFavorites(): Favorites =
    Favorites(
        userId = this.userId?.toId(),
        favorites = this.favorites
    )