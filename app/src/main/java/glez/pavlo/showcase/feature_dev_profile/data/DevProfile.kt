package glez.pavlo.showcase.feature_dev_profile.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dev_profile")
data class DevProfile(
    @PrimaryKey val name: String,
    val lastNames: String,
    val latestProject: String,
    val description: String,
    val role: String,
    val profilePhotoUrl: String
)

@Entity(tableName = "skills")
data class Skill(
    @PrimaryKey val name: String,
    val strength: Int,
)