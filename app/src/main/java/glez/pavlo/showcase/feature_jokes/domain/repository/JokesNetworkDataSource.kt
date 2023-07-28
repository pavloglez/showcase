package glez.pavlo.showcase.feature_jokes.domain.repository

import glez.pavlo.showcase.feature_jokes.data.remote.JokeResponse

interface JokesNetworkDataSource {
    suspend fun getRandomJoke(): JokeResponse
}