package com.dicoding.asclepius.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.AnalyzeEntity

@Dao
interface AnalyzeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrediction(prediction: AnalyzeEntity)

    @Query("SELECT * FROM analyze_entity")
    suspend fun getAllPredictions(): List<AnalyzeEntity>

    @Delete
    suspend fun deletePrediction(prediction: AnalyzeEntity)
}

