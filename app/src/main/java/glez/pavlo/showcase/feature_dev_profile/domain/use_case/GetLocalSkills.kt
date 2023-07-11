package glez.pavlo.showcase.feature_dev_profile.domain.use_case

import glez.pavlo.showcase.feature_dev_profile.domain.repository.DevProfileRepository

class GetLocalSkills(private val repo: DevProfileRepository) {
    suspend operator fun invoke() = repo.getlocalSkills()
}