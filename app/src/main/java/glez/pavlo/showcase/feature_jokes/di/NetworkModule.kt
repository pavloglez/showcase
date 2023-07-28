package glez.pavlo.showcase.feature_jokes.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import glez.pavlo.showcase.feature_jokes.data.repository.JokesNetworkDataSourceImpl
import glez.pavlo.showcase.feature_jokes.domain.repository.JokesNetworkDataSource
import glez.pavlo.showcase.feature_jokes.domain.use_case.GetProgrammingJoke
import glez.pavlo.showcase.feature_jokes.domain.use_case.JokesUseCases
import io.grpc.android.BuildConfig
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    @Provides
    fun provideNetworkDataSource(okhttpCallFactory: Call.Factory) =
        JokesNetworkDataSourceImpl(okhttpCallFactory)



    @Provides
    fun provideUseCases(
        dataSource: JokesNetworkDataSource
    ) = JokesUseCases(
        getProgrammingJoke = GetProgrammingJoke(dataSource)
    )
}

@Module
@InstallIn(SingletonComponent::class)
interface RetrofitModule {

    @Binds
    fun JokesNetworkDataSourceImpl.binds(): JokesNetworkDataSource
}