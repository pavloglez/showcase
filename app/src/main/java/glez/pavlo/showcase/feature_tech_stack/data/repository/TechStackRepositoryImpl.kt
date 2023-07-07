package glez.pavlo.showcase.feature_tech_stack.data.repository

import com.google.firebase.firestore.CollectionReference
import glez.pavlo.showcase.feature_tech_stack.data.local.TechDao
import glez.pavlo.showcase.feature_tech_stack.domain.repository.TechStackRepository
import glez.pavlo.showcase.feature_tech_stack.domain.repository.TechStackResponse
import glez.pavlo.showcase.core.model.Result
import glez.pavlo.showcase.core.model.Result.Failure
import glez.pavlo.showcase.core.model.Result.Success
import glez.pavlo.showcase.feature_dev_profile.data.toTech
import glez.pavlo.showcase.feature_tech_stack.data.Tech
import glez.pavlo.showcase.feature_tech_stack.data.remote.RemoteTech
import kotlinx.coroutines.tasks.await
import java.lang.Exception

import javax.inject.Inject

class TechStackRepositoryImpl @Inject constructor(
    private val techStackRef: CollectionReference,
    private val localDataSource: TechDao
) : TechStackRepository {
    override suspend fun getTechStack(): TechStackResponse {
        var techStackResult: TechStackResponse = Result.Loading
        val techList: MutableList<Tech> = mutableListOf()
        techStackRef.get().addOnSuccessListener { result ->
            techStackResult = if (result == null) {
                Failure(Exception("No data"))
            } else {
                result.toObjects(RemoteTech::class.java).forEach {
                    techList.add(it.toTech())
                }
                Success(techList)
            }
        }.addOnFailureListener {
            Failure(it)
        }.await()
        if (techList.isNotEmpty()) localDataSource.upsertAll(techList)
        return techStackResult
    }

    override suspend fun getLocalTechStack(): TechStackResponse {
        return try {
            val techStack = localDataSource.getTechStack()
            if (techStack.isNotEmpty()) {
                Success(techStack)
            } else {
                Failure(Exception("No data"))
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }
}