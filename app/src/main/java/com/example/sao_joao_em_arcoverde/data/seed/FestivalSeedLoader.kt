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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FestivalSeedLoader(
    private val context: Context,
    private val database: AppDatabase
) {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun seedIfNeeded() {
        seedArtistsIfNeeded()
        seedFestivalDaysIfNeeded()
        seedScheduleIfNeeded()
        seedMapPointsIfNeeded()
        seedEmergencyContactsIfNeeded()
        seedSponsorsIfNeeded()

        seedExtraPolesIfNeeded()
    }

    private suspend fun seedArtistsIfNeeded() {
        if (database.artistDao().countArtists() > 0) return

        val artists = readJsonFile<List<Artist>>("artists.json")

        database.artistDao().insertArtists(
            artists.map { it.toEntity() }
        )
    }

    private suspend fun seedFestivalDaysIfNeeded() {
        if (database.festivalDayDao().countFestivalDays() > 0) return

        val festivalDays = readJsonFile<List<FestivalDay>>("festival_days.json")

        database.festivalDayDao().insertFestivalDays(
            festivalDays.map { it.toEntity() }
        )
    }

    private suspend fun seedScheduleIfNeeded() {
        if (database.scheduleDao().countSchedule() > 0) return

        val schedule = readJsonFile<List<Schedule>>("schedule.json")

        database.scheduleDao().insertSchedule(
            schedule.map { it.toEntity() }
        )
    }

    private suspend fun seedMapPointsIfNeeded() {
        if (database.mapPointDao().countMapPoints() > 0) return

        val mapPoints = readJsonFile<List<MapPoint>>("map_points.json")

        database.mapPointDao().insertMapPoints(
            mapPoints.map { it.toEntity() }
        )
    }

    private suspend fun seedEmergencyContactsIfNeeded() {
        if (database.emergencyContactDao().countEmergencyContacts() > 0) return

        val emergencyContacts = readJsonFile<List<EmergencyContact>>("emergency_contacts.json")

        database.emergencyContactDao().insertEmergencyContacts(
            emergencyContacts.map { it.toEntity() }
        )
    }

    private suspend fun seedSponsorsIfNeeded() {
        if (database.sponsorDao().countSponsors() > 0) return

        val sponsors = readJsonFile<List<Sponsor>>("sponsors.json")

        database.sponsorDao().insertSponsors(
            sponsors.map { it.toEntity() }
        )
    }

    private suspend fun seedExtraPolesIfNeeded() {
        seedExtraArtistsIfNeeded()
        seedExtraSchedule()
        seedExtraMapPointsIfNeeded()
    }

    private suspend fun seedExtraArtistsIfNeeded() {
        val extraArtists = readJsonFile<List<Artist>>("artists_extra_polos.json")

        val newArtists = extraArtists.filter { artist ->
            database.artistDao().getArtistById(artist.id) == null
        }

        if (newArtists.isNotEmpty()) {
            database.artistDao().insertArtists(
                newArtists.map { it.toEntity() }
            )
        }
    }

    private suspend fun seedExtraSchedule() {
        val extraSchedule = readJsonFile<List<Schedule>>("schedule_extra_polos.json")

        database.scheduleDao().insertSchedule(
            extraSchedule.map { it.toEntity() }
        )
    }

    private suspend fun seedExtraMapPointsIfNeeded() {
        val extraMapPoints = readJsonFile<List<MapPoint>>("map_points_extra_polos.json")

        val newMapPoints = extraMapPoints.filter { mapPoint ->
            database.mapPointDao().getMapPointById(mapPoint.id) == null
        }

        if (newMapPoints.isNotEmpty()) {
            database.mapPointDao().insertMapPoints(
                newMapPoints.map { it.toEntity() }
            )
        }
    }

    private inline fun <reified T> readJsonFile(fileName: String): T {
        val jsonString = context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }

        return json.decodeFromString(jsonString)
    }
}