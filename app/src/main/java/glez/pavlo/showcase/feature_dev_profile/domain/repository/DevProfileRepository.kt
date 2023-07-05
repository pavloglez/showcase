package glez.pavlo.showcase.feature_dev_profile.domain.repository

import glez.pavlo.showcase.feature_dev_profile.data.DevProfile
import glez.pavlo.showcase.feature_dev_profile.data.remote.RemoteDevProfile
import glez.pavlo.showcase.feature_dev_profile.domain.model.Result

typealias DevProfileResponse = Result<DevProfile>

interface DevProfileRepository {
    suspend fun getDevProfileFromFirestore(): DevProfileResponse

}