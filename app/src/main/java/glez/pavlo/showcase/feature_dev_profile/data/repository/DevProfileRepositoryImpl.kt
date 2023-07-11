package glez.pavlo.showcase.feature_dev_profile.data.repository

import com.google.firebase.firestore.CollectionReference
import glez.pavlo.showcase.feature_dev_profile.data.DevProfile
import glez.pavlo.showcase.feature_dev_profile.data.local.DevProfileDao
import glez.pavlo.showcase.feature_dev_profile.data.remote.RemoteDevProfile
import glez.pavlo.showcase.feature_dev_profile.data.toDevProfile
import glez.pavlo.showcase.core.model.Result
import glez.pavlo.showcase.core.model.Result.Failure
import glez.pavlo.showcase.core.model.Result.Success
import glez.pavlo.showcase.feature_dev_profile.data.Skill
import glez.pavlo.showcase.feature_dev_profile.data.local.SkillsDao
import glez.pavlo.showcase.feature_dev_profile.data.remote.RemoteSkill
import glez.pavlo.showcase.feature_dev_profile.data.toSkill
import glez.pavlo.showcase.feature_dev_profile.domain.repository.DevProfileRepository
import glez.pavlo.showcase.feature_dev_profile.domain.repository.DevProfileResponse
import glez.pavlo.showcase.feature_dev_profile.domain.repository.SkillsResponse
import kotlinx.coroutines.tasks.await

import java.lang.Exception
import javax.inject.Inject

class DevProfileRepositoryImpl @Inject constructor(
    private val devProfileRef: CollectionReference,
    private val skillsRef: CollectionReference,
    private val localDevDataSource: DevProfileDao,
    private val localSkillsDataSource: SkillsDao
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
            localDevDataSource.upsert(it)
        }
        return profileResult
    }

    override suspend fun getLocalDevProfile(): DevProfileResponse {
        return try {
            localDevDataSource.getDevProfile()?.let {
                return Success(it)
            }
            Failure(Exception("No data"))
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getSkillsFromFirestore(): SkillsResponse {
        var skillsResult: SkillsResponse = Result.Loading
        val skillsList: MutableList<Skill> = mutableListOf()
        skillsRef.get().addOnSuccessListener { result ->
            skillsResult = if (result == null) {
                Failure(Exception("No data"))
            } else {
                result.toObjects(RemoteSkill::class.java).forEach {
                    skillsList.add(it.toSkill())
                }
                Success(skillsList)
            }
        }.addOnFailureListener {
            Failure(it)
        }.await()
        if (skillsList.isNotEmpty()) localSkillsDataSource.upsertAll(skillsList)
        return skillsResult
    }

    override suspend fun getlocalSkills(): SkillsResponse {
        return try {
            localSkillsDataSource.getSkills().let {
                return if(it.isNotEmpty()) Success(it) else Failure(Exception("No data"))
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

}