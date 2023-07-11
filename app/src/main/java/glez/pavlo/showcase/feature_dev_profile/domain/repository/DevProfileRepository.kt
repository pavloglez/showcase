package glez.pavlo.showcase.feature_dev_profile.domain.repository

import glez.pavlo.showcase.feature_dev_profile.data.DevProfile
import glez.pavlo.showcase.core.model.Result
import glez.pavlo.showcase.feature_dev_profile.data.Skill

typealias DevProfileResponse = Result<DevProfile>
typealias SkillsResponse = Result<List<Skill>>

interface DevProfileRepository {
    suspend fun getDevProfileFromFirestore(): DevProfileResponse
    suspend fun getLocalDevProfile(): DevProfileResponse
    suspend fun getSkillsFromFirestore(): SkillsResponse
    suspend fun getlocalSkills(): SkillsResponse
}