package glez.pavlo.showcase.feature_jokes.data.repository

import glez.pavlo.showcase.feature_jokes.data.remote.JokeResponse
import glez.pavlo.showcase.feature_jokes.domain.repository.JokesNetworkDataSource
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton


private interface JokesAPI {

    @GET("jokes/programming/random")
    suspend fun getRandomJoke(): JokeResponse
}

@Singleton
class JokesNetworkDataSourceImpl @Inject constructor(
    okhttpFactory: Call.Factory,
): JokesNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl("https://official-joke-api.appspot.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .callFactory(okhttpFactory)
        .build()
        .create<JokesAPI>()

    override suspend fun getRandomJoke(): JokeResponse {
        return networkApi.getRandomJoke()
    }
}