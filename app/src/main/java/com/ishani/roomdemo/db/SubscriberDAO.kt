package com.ishani.roomdemo.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {

    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber): Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM subscriber_table")
    fun getAllSubscribersData(): Flow<List<Subscriber>>
}