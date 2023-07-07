package glez.pavlo.showcase.feature_dev_profile.data.repository

import com.google.firebase.firestore.CollectionReference
import glez.pavlo.showcase.feature_dev_profile.data.DevProfile
import glez.pavlo.showcase.feature_dev_profile.data.local.DevProfileDao
import glez.pavlo.showcase.feature_dev_profile.data.remote.RemoteDevProfile
import glez.pavlo.showcase.feature_dev_profile.data.toDevProfile
import glez.pavlo.showcase.core.model.Result
import glez.pavlo.showcase.core.model.Result.Failure
import glez.pavlo.showcase.core.model.Result.Success
import glez.pavlo.showcase.feature_dev_profile.domain.repository.DevProfileRepository
import glez.pavlo.showcase.feature_dev_profile.domain.repository.DevProfileResponse
import kotlinx.coroutines.tasks.await

import java.lang.Exception
import javax.inject.Inject

class DevProfileRepositoryImpl @Inject constructor(
    private val devProfileRef: CollectionReference,
    private val localDataSource: DevProfileDao
    ) : DevProfileRepository {
    override suspend fun getDevProfileFromFirestore(): DevProfileResponse {
        var profileResult: DevProfileResponse = Result.Loading
        var devProfile: DevProfile? = null
        devProfileRef.get().addOnSuccessListener { result ->
            profileResult = if (result == null) {
                Failure(Exception("No data"))
            } else {
                devProfile = result.first().toObject(RemoteDevProfile::class.java).toDevProfile()
                Success(devProfile!!)
            }
        }.addOnFailureListener {
            Failure(it)
        }.await()
        devProfile?.let {
            localDataSource.upsert(it)
        }
        return profileResult
    }

    override suspend fun getLocalDevProfile(): DevProfileResponse {
        return try {
            localDataSource.getDevProfile()?.let {
                return Success(it)
            }
            Failure(Exception("No data"))
        } catch (e: Exception) {
            Failure(e)
        }
    }

}