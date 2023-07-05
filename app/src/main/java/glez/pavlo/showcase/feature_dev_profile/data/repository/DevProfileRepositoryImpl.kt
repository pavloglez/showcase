package glez.pavlo.showcase.feature_dev_profile.data.repository

import com.google.firebase.firestore.CollectionReference
import glez.pavlo.showcase.feature_dev_profile.data.remote.RemoteDevProfile
import glez.pavlo.showcase.feature_dev_profile.data.toDevProfile
import glez.pavlo.showcase.feature_dev_profile.domain.model.Result
import glez.pavlo.showcase.feature_dev_profile.domain.model.Result.Failure
import glez.pavlo.showcase.feature_dev_profile.domain.model.Result.Success
import glez.pavlo.showcase.feature_dev_profile.domain.repository.DevProfileRepository
import glez.pavlo.showcase.feature_dev_profile.domain.repository.DevProfileResponse
import kotlinx.coroutines.tasks.await

import java.lang.Exception
import javax.inject.Inject

class DevProfileRepositoryImpl @Inject constructor(private val devProfileRef: CollectionReference) : DevProfileRepository {
    override suspend fun getDevProfileFromFirestore(): DevProfileResponse {
        var profileResult: DevProfileResponse = Result.Loading
        devProfileRef.get().addOnSuccessListener { result ->
            profileResult = if (result == null) {
                Failure(Exception("No data"))
            } else {
                Success(result.first().toObject(RemoteDevProfile::class.java).toDevProfile())
            }
        }.addOnFailureListener {
            Failure(it)
        }.await()
        return profileResult
    }

}