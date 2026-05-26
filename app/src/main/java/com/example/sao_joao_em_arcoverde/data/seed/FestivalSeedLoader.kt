package com.example.sao_joao_em_arcoverde.data.seed

import android.content.Context
import com.example.sao_joao_em_arcoverde.data.local.AppDatabase
import com.example.sao_joao_em_arcoverde.data.mapper.toEntity
import com.example.sao_joao_em_arcoverde.data.model.Artist
import com.example.sao_joao_em_arcoverde.data.model.EmergencyContact
import com.example.sao_joao_em_arcoverde.data.model.FestivalDay
import com.example.sao_joao_em_arcoverde.data.model.MapPoint
import com.example.sao_joao_em_arcoverde.data.model.Schedule
import com.example.sao_joao_em_arcoverde.data.model.Sponsor
import kotlinx.serialization.json.Json

class FestivalSeedLoader(
    private val context: Context,
    private val database: AppDatabase
) {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    suspend fun seedIfNeeded() {
        if (isDatabaseAlreadySeeded()) {
            return
        }

        seedDatabase()
    }

    private suspend fun isDatabaseAlreadySeeded(): Boolean {
        val artistsCount = database.artistDao().countArtists()
        val daysCount = database.festivalDayDao().countFestivalDays()
        val scheduleCount = database.scheduleDao().countSchedule()
        val mapPointsCount = database.mapPointDao().countMapPoints()
        val contactsCount = database.emergencyContactDao().countEmergencyContacts()

        return artistsCount > 0 &&
                daysCount > 0 &&
                scheduleCount > 0 &&
                mapPointsCount > 0 &&
                contactsCount > 0
    }

    private suspend fun seedDatabase() {
        val artists = readJsonFile<List<Artist>>("artists.json")
        val festivalDays = readJsonFile<List<FestivalDay>>("festival_days.json")
        val schedule = readJsonFile<List<Schedule>>("schedule.json")
        val mapPoints = readJsonFile<List<MapPoint>>("map_points.json")
        val emergencyContacts = readJsonFile<List<EmergencyContact>>("emergency_contacts.json")
        val sponsors = readJsonFile<List<Sponsor>>("sponsors.json")

        database.artistDao().insertArtists(
            artists.map { it.toEntity() }
        )

        database.festivalDayDao().insertFestivalDays(
            festivalDays.map { it.toEntity() }
        )

        database.scheduleDao().insertSchedule(
            schedule.map { it.toEntity() }
        )

        database.mapPointDao().insertMapPoints(
            mapPoints.map { it.toEntity() }
        )

        database.emergencyContactDao().insertEmergencyContacts(
            emergencyContacts.map { it.toEntity() }
        )

        database.sponsorDao().insertSponsors(
            sponsors.map { it.toEntity() }
        )
    }

    private inline fun <reified T> readJsonFile(fileName: String): T {
        val jsonString = context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }

        return json.decodeFromString(jsonString)
    }
}