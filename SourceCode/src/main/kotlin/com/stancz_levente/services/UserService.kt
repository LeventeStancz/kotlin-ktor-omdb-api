package com.stancz_levente.services

import com.stancz_levente.configs.ConfigModule
import com.stancz_levente.models.User
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId
import org.mindrot.jbcrypt.BCrypt

// Service for managing user-related operations
class UserService {
    private val client = KMongo.createClient(ConfigModule.getProperty("database.dbConString"))
    private val database = client.getDatabase(ConfigModule.getProperty("database.dbName"))
    private val userCollection = database.getCollection<User>()

    // Insert new user into db
    fun create(user: User): Id<User>? {
        val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())
        val userWithHashedPassword = user.copy(password = hashedPassword)

        userCollection.insertOne(userWithHashedPassword)
        return userWithHashedPassword.id
    }
    // Find a user by ID
    fun findById(id: String): User? {
        val bsonId: Id<User> = ObjectId(id).toId()
        return userCollection
            .findOne(User::id eq bsonId)
    }
    // Update user data by ID
    fun updateById(id: String, request: User): Boolean =
        findById(id)
        ?.let { user ->
            val updateResult = userCollection.replaceOne(user.copy(username = request.username, email = request.email))
            updateResult.modifiedCount == 1L
        } ?: false
    // Delete a user by ID
    fun deleteById(id: String): Boolean {
        val deleteResult = userCollection.deleteOneById(ObjectId(id))
        return deleteResult.deletedCount == 1L
    }
}