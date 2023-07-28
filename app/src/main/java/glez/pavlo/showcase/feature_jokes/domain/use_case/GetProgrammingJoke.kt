package glez.pavlo.showcase.feature_jokes.domain.use_case

import glez.pavlo.showcase.feature_jokes.domain.repository.JokesNetworkDataSource

class GetProgrammingJoke(private val source: JokesNetworkDataSource) {
    suspend operator fun invoke() = source.getRandomJoke()
}