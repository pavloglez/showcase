package glez.pavlo.showcase.feature_dev_profile.domain.repository

import glez.pavlo.showcase.feature_dev_profile.data.DevProfile
import glez.pavlo.showcase.core.model.Result

typealias DevProfileResponse = Result<DevProfile>

interface DevProfileRepository {
    suspend fun getDevProfileFromFirestore(): DevProfileResponse
    suspend fun getLocalDevProfile(): DevProfileResponse
}