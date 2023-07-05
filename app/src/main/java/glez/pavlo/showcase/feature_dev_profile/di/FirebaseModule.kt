package glez.pavlo.showcase.feature_dev_profile.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import glez.pavlo.showcase.core.Constants.DEV_PROFILE
import glez.pavlo.showcase.feature_dev_profile.data.repository.DevProfileRepositoryImpl
import glez.pavlo.showcase.feature_dev_profile.domain.repository.DevProfileRepository
import glez.pavlo.showcase.feature_dev_profile.domain.use_case.GetDevProfile
import glez.pavlo.showcase.feature_dev_profile.domain.use_case.UseCases

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideDevProfileRef() = Firebase.firestore.collection(DEV_PROFILE)

    @Provides
    fun provideDevProfileRepository(
        devProfileRef: CollectionReference
    ): DevProfileRepository = DevProfileRepositoryImpl(devProfileRef)

    @Provides
    fun provideUseCases(
        repo: DevProfileRepository
    ) = UseCases(
        getDevProfile = GetDevProfile(repo)
    )
}