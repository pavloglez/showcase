package glez.pavlo.showcase.feature_tech_stack.domain.repository

import glez.pavlo.showcase.core.model.Result
import glez.pavlo.showcase.feature_tech_stack.data.Tech


typealias TechStackResponse = Result<List<Tech>>

interface TechStackRepository {
    suspend fun getTechStack(): TechStackResponse
    suspend fun getLocalTechStack(): TechStackResponse
}