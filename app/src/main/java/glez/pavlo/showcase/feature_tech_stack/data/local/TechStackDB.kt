package glez.pavlo.showcase.feature_tech_stack.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import glez.pavlo.showcase.feature_tech_stack.data.Tech

@Database(entities = [Tech::class], version = 1)
abstract class TechStackDB : RoomDatabase() {
    abstract fun techDao(): TechDao
}