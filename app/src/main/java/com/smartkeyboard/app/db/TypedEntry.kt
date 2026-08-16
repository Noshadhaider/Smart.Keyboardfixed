package com.smartkeyboard.app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "typed_entries")
data class TypedEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val appPackage: String,
    val timestamp: Long = System.currentTimeMillis(),
    val synced: Boolean = false
)
