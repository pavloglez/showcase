package glez.pavlo.showcase.feature_tech_stack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tech_stack")
data class Tech(
    @PrimaryKey val name: String,
    val isImplemented: Boolean
)