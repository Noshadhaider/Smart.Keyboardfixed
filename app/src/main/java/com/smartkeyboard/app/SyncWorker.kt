package com.smartkeyboard.app

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.smartkeyboard.app.db.AppDatabase
import kotlinx.coroutines.tasks.await
import java.util.Date

class SyncWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val dao = AppDatabase.get(applicationContext).typedEntryDao()
        val db = Firebase.firestore
        val unsynced = dao.getUnsynced()
        if (unsynced.isEmpty()) return Result.success()
        for (entry in unsynced) {
            try {
                val data = hashMapOf(
                    "text" to entry.text,
                    "app" to entry.appPackage,
                    "timestamp" to Date(entry.timestamp)
                )
                db.collection("typed_entries").add(data).await()
                dao.markSynced(entry.id)
            } catch (e: Exception) {
                return Result.retry()
            }
        }
        return Result.success()
    }
}
