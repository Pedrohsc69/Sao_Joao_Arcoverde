package com.example.sao_joao_em_arcoverde.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sao_joao_em_arcoverde.data.local.entity.ScheduleEntity

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedule ORDER BY date ASC, time ASC")
    suspend fun getAllSchedule(): List<ScheduleEntity>

    @Query("SELECT * FROM schedule WHERE id = :scheduleId LIMIT 1")
    suspend fun getScheduleById(scheduleId: String): ScheduleEntity?

    @Query("SELECT * FROM schedule WHERE date = :date ORDER BY time ASC")
    suspend fun getScheduleByDate(date: String): List<ScheduleEntity>

    @Query(
        """
        SELECT * FROM schedule 
        WHERE date = :date 
        AND stageName = :stageName 
        ORDER BY time ASC
        """
    )
    suspend fun getScheduleByDateAndStage(
        date: String,
        stageName: String
    ): List<ScheduleEntity>

    @Query("SELECT * FROM schedule WHERE artistId = :artistId ORDER BY date ASC, time ASC")
    suspend fun getScheduleByArtistId(artistId: String): List<ScheduleEntity>

    @Query("SELECT * FROM schedule WHERE isHeadliner = 1 ORDER BY date ASC, time ASC")
    suspend fun getHeadliners(): List<ScheduleEntity>

    @Query("SELECT * FROM schedule WHERE isBookmarked = 1 ORDER BY date ASC, time ASC")
    suspend fun getBookmarkedSchedule(): List<ScheduleEntity>

    @Query(
        """
        SELECT * FROM schedule 
        WHERE artistName LIKE '%' || :query || '%' 
        OR genre LIKE '%' || :query || '%' 
        OR stageName LIKE '%' || :query || '%'
        ORDER BY date ASC, time ASC
        """
    )
    suspend fun searchSchedule(query: String): List<ScheduleEntity>

    @Query("UPDATE schedule SET isBookmarked = :isBookmarked WHERE id = :scheduleId")
    suspend fun updateBookmarkStatus(
        scheduleId: String,
        isBookmarked: Boolean
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: List<ScheduleEntity>)

    @Query("DELETE FROM schedule")
    suspend fun clearSchedule()

    @Query("SELECT COUNT(*) FROM schedule")
    suspend fun countSchedule(): Int
}