package glez.pavlo.showcase.feature_jokes.domain

import glez.pavlo.showcase.feature_jokes.data.remote.JokeResponse
import retrofit2.http.GET

private interface JokesAPI {

    @GET("jokes/random")
    suspend fun getRandomJoke(): JokeResponse
}