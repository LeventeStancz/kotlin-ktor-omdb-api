package com.stancz_levente.services

import com.mongodb.client.model.UpdateOptions
import com.stancz_levente.configs.ConfigModule
import com.stancz_levente.models.Favorites
import com.stancz_levente.models.User
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

// Service for managing favorites-related operations
class FavoritesService {
    private val client = KMongo.createClient(ConfigModule.getProperty("database.dbConString"))
    private val database = client.getDatabase(ConfigModule.getProperty("database.dbName"))
    private val favoritesCollection = database.getCollection<Favorites>()

    // Find favorites by user ID
    fun findFavoritesByUserId(id: String): Favorites? {
        val userId: Id<User> = ObjectId(id).toId()
        return favoritesCollection
            .findOne(Favorites::userId eq userId)
    }
    // Append a movie to user favorites
    fun appendFavoritesById(id: String, movieId: String): Boolean {
        val userId: Id<User> = ObjectId(id).toId()
        val updateResult = favoritesCollection.updateOne(
            Favorites::userId eq userId,
            addToSet(Favorites::favorites, movieId),
            UpdateOptions().upsert(true)
        )
        return updateResult.modifiedCount == 1L || updateResult.upsertedId != null
    }
    // Remove a movie from user favorites
    fun removeFromFavoritesById(id: String, movieId: String): Boolean {
        val userId: Id<User> = ObjectId(id).toId()
        val updateResult = favoritesCollection.updateOne(
            Favorites::userId eq userId,
            pull(Favorites::favorites, movieId)
        )
        return updateResult.modifiedCount == 1L
    }
}