package com.example.sao_joao_em_arcoverde.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sao_joao_em_arcoverde.data.local.entity.MapPointEntity

@Dao
interface MapPointDao {

    @Query("SELECT * FROM map_points ORDER BY name ASC")
    suspend fun getAllMapPoints(): List<MapPointEntity>

    @Query("SELECT * FROM map_points WHERE id = :mapPointId LIMIT 1")
    suspend fun getMapPointById(mapPointId: String): MapPointEntity?

    @Query("SELECT * FROM map_points WHERE type = :type ORDER BY name ASC")
    suspend fun getMapPointsByType(type: String): List<MapPointEntity>

    @Query(
        """
        SELECT * FROM map_points 
        WHERE name LIKE '%' || :query || '%' 
        OR description LIKE '%' || :query || '%'
        ORDER BY name ASC
        """
    )
    suspend fun searchMapPoints(query: String): List<MapPointEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMapPoints(mapPoints: List<MapPointEntity>)

    @Query("DELETE FROM map_points")
    suspend fun clearMapPoints()

    @Query("SELECT COUNT(*) FROM map_points")
    suspend fun countMapPoints(): Int
}