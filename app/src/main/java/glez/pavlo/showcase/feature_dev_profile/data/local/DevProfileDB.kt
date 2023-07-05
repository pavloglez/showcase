package glez.pavlo.showcase.feature_dev_profile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import glez.pavlo.showcase.feature_dev_profile.data.DevProfile

@Database(entities = [DevProfile::class], version = 1)
abstract class DevProfileDB: RoomDatabase() {
    abstract fun devProfileDao(): DevProfileDao
}