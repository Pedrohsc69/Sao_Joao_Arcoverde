package com.example.sao_joao_em_arcoverde.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sao_joao_em_arcoverde.data.local.dao.ArtistDao
import com.example.sao_joao_em_arcoverde.data.local.dao.EmergencyContactDao
import com.example.sao_joao_em_arcoverde.data.local.dao.FestivalDayDao
import com.example.sao_joao_em_arcoverde.data.local.dao.MapPointDao
import com.example.sao_joao_em_arcoverde.data.local.dao.ScheduleDao
import com.example.sao_joao_em_arcoverde.data.local.dao.SponsorDao
import com.example.sao_joao_em_arcoverde.data.local.entity.ArtistEntity
import com.example.sao_joao_em_arcoverde.data.local.entity.EmergencyContactEntity
import com.example.sao_joao_em_arcoverde.data.local.entity.FestivalDayEntity
import com.example.sao_joao_em_arcoverde.data.local.entity.MapPointEntity
import com.example.sao_joao_em_arcoverde.data.local.entity.ScheduleEntity
import com.example.sao_joao_em_arcoverde.data.local.entity.SponsorEntity

@Database(
    entities = [
        ArtistEntity::class,
        FestivalDayEntity::class,
        ScheduleEntity::class,
        MapPointEntity::class,
        EmergencyContactEntity::class,
        SponsorEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun artistDao(): ArtistDao

    abstract fun festivalDayDao(): FestivalDayDao

    abstract fun scheduleDao(): ScheduleDao

    abstract fun mapPointDao(): MapPointDao

    abstract fun emergencyContactDao(): EmergencyContactDao

    abstract fun sponsorDao(): SponsorDao

    companion object {
        private const val DATABASE_NAME = "sao_joao_arcoverde.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}