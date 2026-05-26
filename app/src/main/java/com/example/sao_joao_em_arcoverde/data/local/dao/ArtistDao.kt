package com.example.sao_joao_em_arcoverde.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sao_joao_em_arcoverde.data.local.entity.ArtistEntity

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artists ORDER BY name ASC")
    suspend fun getAllArtists(): List<ArtistEntity>

    @Query("SELECT * FROM artists WHERE id = :artistId LIMIT 1")
    suspend fun getArtistById(artistId: String): ArtistEntity?

    @Query("SELECT * FROM artists WHERE isFeatured = 1 ORDER BY name ASC")
    suspend fun getFeaturedArtists(): List<ArtistEntity>

    @Query(
        """
        SELECT * FROM artists 
        WHERE name LIKE '%' || :query || '%' 
        OR genre LIKE '%' || :query || '%' 
        ORDER BY name ASC
        """
    )
    suspend fun searchArtists(query: String): List<ArtistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<ArtistEntity>)

    @Query("DELETE FROM artists")
    suspend fun clearArtists()

    @Query("SELECT COUNT(*) FROM artists")
    suspend fun countArtists(): Int
}