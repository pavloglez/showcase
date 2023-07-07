package glez.pavlo.showcase.feature_tech_stack.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import glez.pavlo.showcase.feature_tech_stack.data.Tech

@Dao
interface TechDao {
    @Query("SELECT * FROM tech_stack")
    suspend fun getTechStack(): List<Tech>

    @Upsert
    suspend fun upsert(tech: Tech)

    @Upsert
    suspend fun upsertAll(tech: List<Tech>)
}