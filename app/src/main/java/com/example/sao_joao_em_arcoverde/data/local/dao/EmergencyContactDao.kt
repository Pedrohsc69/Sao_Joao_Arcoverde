package com.example.sao_joao_em_arcoverde.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sao_joao_em_arcoverde.data.local.entity.EmergencyContactEntity

@Dao
interface EmergencyContactDao {

    @Query("SELECT * FROM emergency_contacts ORDER BY name ASC")
    suspend fun getAllEmergencyContacts(): List<EmergencyContactEntity>

    @Query("SELECT * FROM emergency_contacts WHERE id = :contactId LIMIT 1")
    suspend fun getEmergencyContactById(contactId: String): EmergencyContactEntity?

    @Query("SELECT * FROM emergency_contacts WHERE type = :type ORDER BY name ASC")
    suspend fun getEmergencyContactsByType(type: String): List<EmergencyContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergencyContacts(contacts: List<EmergencyContactEntity>)

    @Query("DELETE FROM emergency_contacts")
    suspend fun clearEmergencyContacts()

    @Query("SELECT COUNT(*) FROM emergency_contacts")
    suspend fun countEmergencyContacts(): Int
}