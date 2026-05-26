package com.example.sao_joao_em_arcoverde.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sao_joao_em_arcoverde.data.local.entity.SponsorEntity

@Dao
interface SponsorDao {

    @Query("SELECT * FROM sponsors ORDER BY name ASC")
    suspend fun getAllSponsors(): List<SponsorEntity>

    @Query("SELECT * FROM sponsors WHERE id = :sponsorId LIMIT 1")
    suspend fun getSponsorById(sponsorId: String): SponsorEntity?

    @Query("SELECT * FROM sponsors WHERE category = :category ORDER BY name ASC")
    suspend fun getSponsorsByCategory(category: String): List<SponsorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSponsors(sponsors: List<SponsorEntity>)

    @Query("DELETE FROM sponsors")
    suspend fun clearSponsors()

    @Query("SELECT COUNT(*) FROM sponsors")
    suspend fun countSponsors(): Int
}