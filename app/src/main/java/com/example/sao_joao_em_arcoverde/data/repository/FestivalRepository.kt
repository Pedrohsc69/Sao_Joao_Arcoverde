package com.example.sao_joao_em_arcoverde.data.repository

import com.example.sao_joao_em_arcoverde.data.local.AppDatabase
import com.example.sao_joao_em_arcoverde.data.mapper.toModel
import com.example.sao_joao_em_arcoverde.data.model.Artist
import com.example.sao_joao_em_arcoverde.data.model.EmergencyContact
import com.example.sao_joao_em_arcoverde.data.model.EmergencyContactType
import com.example.sao_joao_em_arcoverde.data.model.FestivalDay
import com.example.sao_joao_em_arcoverde.data.model.MapPoint
import com.example.sao_joao_em_arcoverde.data.model.MapPointType
import com.example.sao_joao_em_arcoverde.data.model.Schedule
import com.example.sao_joao_em_arcoverde.data.model.Sponsor
import com.example.sao_joao_em_arcoverde.data.seed.FestivalSeedLoader

class FestivalRepository(
    private val database: AppDatabase,
    private val seedLoader: FestivalSeedLoader
) {
    private var hasInitialized = false

    private suspend fun ensureSeeded() {
        if (!hasInitialized) {
            seedLoader.seedIfNeeded()
            hasInitialized = true
        }
    }

    suspend fun getAllArtists(): List<Artist> {
        ensureSeeded()

        return database.artistDao()
            .getAllArtists()
            .map { it.toModel() }
    }

    suspend fun getArtistById(artistId: String): Artist? {
        ensureSeeded()

        return database.artistDao()
            .getArtistById(artistId)
            ?.toModel()
    }

    suspend fun getFeaturedArtists(): List<Artist> {
        ensureSeeded()

        return database.artistDao()
            .getFeaturedArtists()
            .map { it.toModel() }
    }

    suspend fun searchArtists(query: String): List<Artist> {
        ensureSeeded()

        return database.artistDao()
            .searchArtists(query.trim())
            .map { it.toModel() }
    }

    suspend fun getFestivalDays(): List<FestivalDay> {
        ensureSeeded()

        return database.festivalDayDao()
            .getAllFestivalDays()
            .map { it.toModel() }
    }

    suspend fun getAllSchedule(): List<Schedule> {
        ensureSeeded()

        return database.scheduleDao()
            .getAllSchedule()
            .map { it.toModel() }
    }

    suspend fun getScheduleByDate(date: String): List<Schedule> {
        ensureSeeded()

        return database.scheduleDao()
            .getScheduleByDate(date)
            .map { it.toModel() }
            .sortedWith(scheduleTimeComparator())
    }

    suspend fun getScheduleByDateAndStage(
        date: String,
        stageName: String
    ): List<Schedule> {
        ensureSeeded()

        return database.scheduleDao()
            .getScheduleByDateAndStage(
                date = date,
                stageName = stageName
            )
            .map { it.toModel() }
            .sortedWith(scheduleTimeComparator())
    }

    suspend fun getScheduleByArtistId(artistId: String): List<Schedule> {
        ensureSeeded()

        return database.scheduleDao()
            .getScheduleByArtistId(artistId)
            .map { it.toModel() }
            .sortedWith(compareBy<Schedule> { it.date }.then(scheduleTimeComparator()))
    }

    suspend fun getHeadliners(): List<Schedule> {
        ensureSeeded()

        return database.scheduleDao()
            .getHeadliners()
            .map { it.toModel() }
            .sortedWith(compareBy<Schedule> { it.date }.then(scheduleTimeComparator()))
    }

    suspend fun getBookmarkedSchedule(): List<Schedule> {
        ensureSeeded()

        return database.scheduleDao()
            .getBookmarkedSchedule()
            .map { it.toModel() }
            .sortedWith(compareBy<Schedule> { it.date }.then(scheduleTimeComparator()))
    }

    suspend fun searchSchedule(query: String): List<Schedule> {
        ensureSeeded()

        return database.scheduleDao()
            .searchSchedule(query.trim())
            .map { it.toModel() }
            .sortedWith(compareBy<Schedule> { it.date }.then(scheduleTimeComparator()))
    }

    suspend fun updateScheduleBookmark(
        scheduleId: String,
        isBookmarked: Boolean
    ) {
        ensureSeeded()

        database.scheduleDao().updateBookmarkStatus(
            scheduleId = scheduleId,
            isBookmarked = isBookmarked
        )
    }

    suspend fun getTodayStagePreview(
        date: String = "2026-06-13",
        limit: Int = 3
    ): List<Schedule> {
        ensureSeeded()

        return getScheduleByDate(date)
            .take(limit)
    }

    suspend fun getAllMapPoints(): List<MapPoint> {
        ensureSeeded()

        return database.mapPointDao()
            .getAllMapPoints()
            .map { it.toModel() }
    }

    suspend fun getMapPointById(mapPointId: String): MapPoint? {
        ensureSeeded()

        return database.mapPointDao()
            .getMapPointById(mapPointId)
            ?.toModel()
    }

    suspend fun getMapPointsByType(type: MapPointType): List<MapPoint> {
        ensureSeeded()

        return database.mapPointDao()
            .getMapPointsByType(type.name)
            .map { it.toModel() }
    }

    suspend fun searchMapPoints(query: String): List<MapPoint> {
        ensureSeeded()

        return database.mapPointDao()
            .searchMapPoints(query.trim())
            .map { it.toModel() }
    }

    suspend fun getAllEmergencyContacts(): List<EmergencyContact> {
        ensureSeeded()

        return database.emergencyContactDao()
            .getAllEmergencyContacts()
            .map { it.toModel() }
    }

    suspend fun getEmergencyContactsByType(
        type: EmergencyContactType
    ): List<EmergencyContact> {
        ensureSeeded()

        return database.emergencyContactDao()
            .getEmergencyContactsByType(type.name)
            .map { it.toModel() }
    }

    suspend fun getAllSponsors(): List<Sponsor> {
        ensureSeeded()

        return database.sponsorDao()
            .getAllSponsors()
            .map { it.toModel() }
    }

    suspend fun getSponsorsByCategory(category: String): List<Sponsor> {
        ensureSeeded()

        return database.sponsorDao()
            .getSponsorsByCategory(category)
            .map { it.toModel() }
    }

    private fun scheduleTimeComparator(): Comparator<Schedule> {
        return compareBy { schedule ->
            normalizeScheduleTime(schedule.time)
        }
    }

    private fun normalizeScheduleTime(time: String): Int {
        val parts = time.split(":")
        val hour = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0

        val normalizedHour = if (hour < 6) {
            hour + 24
        } else {
            hour
        }

        return normalizedHour * 60 + minute
    }
}