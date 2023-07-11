package glez.pavlo.showcase.feature_dev_profile.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import glez.pavlo.showcase.feature_dev_profile.data.DevProfile
import glez.pavlo.showcase.feature_dev_profile.data.Skill

@Dao
interface DevProfileDao {
    @Query("SELECT * FROM dev_profile LIMIT 1")
    suspend fun getDevProfile(): DevProfile?

    @Upsert
    suspend fun upsert(devProfile: DevProfile)
}

@Dao
interface SkillsDao {
    @Query("SELECT * FROM skills")
    suspend fun getSkills(): List<Skill>

    @Upsert
    suspend fun upsertAll(skills: List<Skill>)
}