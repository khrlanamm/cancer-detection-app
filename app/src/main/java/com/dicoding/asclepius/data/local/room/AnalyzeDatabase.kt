package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.AnalyzeEntity

@Database(entities = [AnalyzeEntity::class], version = 2, exportSchema = false)
abstract class AnalyzeDatabase : RoomDatabase() {

    abstract fun analyzeDao(): AnalyzeDao

    companion object {
        @Volatile
        private var INSTANCE: AnalyzeDatabase? = null

        fun getDatabase(context: Context): AnalyzeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnalyzeDatabase::class.java,
                    "analyze_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
