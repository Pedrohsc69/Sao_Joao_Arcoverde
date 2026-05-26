package com.example.sao_joao_em_arcoverde.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sao_joao_em_arcoverde.data.local.entity.FestivalDayEntity

@Dao
interface FestivalDayDao {

    @Query("SELECT * FROM festival_days ORDER BY date ASC")
    suspend fun getAllFestivalDays(): List<FestivalDayEntity>

    @Query("SELECT * FROM festival_days WHERE id = :dayId LIMIT 1")
    suspend fun getFestivalDayById(dayId: String): FestivalDayEntity?

    @Query("SELECT * FROM festival_days WHERE date = :date LIMIT 1")
    suspend fun getFestivalDayByDate(date: String): FestivalDayEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFestivalDays(days: List<FestivalDayEntity>)

    @Query("DELETE FROM festival_days")
    suspend fun clearFestivalDays()

    @Query("SELECT COUNT(*) FROM festival_days")
    suspend fun countFestivalDays(): Int
}