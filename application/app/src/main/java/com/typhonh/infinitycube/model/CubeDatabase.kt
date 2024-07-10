package com.typhonh.infinitycube.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.typhonh.infinitycube.model.entity.CRGB
import com.typhonh.infinitycube.model.entity.Preset
import com.typhonh.infinitycube.model.serializer.DatabaseConverters

@Database(
    entities = [ Preset::class, CRGB::class ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(DatabaseConverters::class)
abstract class CubeDatabase: RoomDatabase() {
    abstract fun presetDao(): PresetDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CubeDatabase? = null

        fun getDatabase(context: Context): CubeDatabase {
            // if the INSTANCE is not null, then return
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CubeDatabase::class.java,
                    "infinity-cube_database"
                ).build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }
}