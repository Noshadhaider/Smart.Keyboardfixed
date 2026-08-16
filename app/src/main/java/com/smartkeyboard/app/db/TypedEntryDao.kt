package com.smartkeyboard.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TypedEntryDao {
    @Insert
    suspend fun insert(entry: TypedEntry)

    @Query("SELECT * FROM typed_entries WHERE synced = 0")
    suspend fun getUnsynced(): List<TypedEntry>

    @Query("UPDATE typed_entries SET synced = 1 WHERE id = :id")
    suspend fun markSynced(id: Int)
}
