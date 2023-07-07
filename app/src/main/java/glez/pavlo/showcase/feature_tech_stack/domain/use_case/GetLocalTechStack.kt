package glez.pavlo.showcase.feature_tech_stack.domain.use_case

import glez.pavlo.showcase.feature_tech_stack.domain.repository.TechStackRepository

class GetLocalTechStack(private val repo: TechStackRepository) {
    suspend operator fun invoke() = repo.getLocalTechStack()
}