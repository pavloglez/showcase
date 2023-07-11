package glez.pavlo.showcase.feature_dev_profile.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import glez.pavlo.showcase.core.Constants.DEV_PROFILE_REF
import glez.pavlo.showcase.core.Constants.SKILLS_REF
import glez.pavlo.showcase.feature_dev_profile.data.local.DevProfileDB
import glez.pavlo.showcase.feature_dev_profile.data.local.DevProfileDao
import glez.pavlo.showcase.feature_dev_profile.data.local.SkillsDao
import glez.pavlo.showcase.feature_dev_profile.data.repository.DevProfileRepositoryImpl
import glez.pavlo.showcase.feature_dev_profile.domain.repository.DevProfileRepository
import glez.pavlo.showcase.feature_dev_profile.domain.use_case.GetDevProfile
import glez.pavlo.showcase.feature_dev_profile.domain.use_case.GetLocalDevProfile
import glez.pavlo.showcase.feature_dev_profile.domain.use_case.GetLocalSkills
import glez.pavlo.showcase.feature_dev_profile.domain.use_case.GetSkills
import glez.pavlo.showcase.feature_dev_profile.domain.use_case.UseCases
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DevProfileDB {
        return Room.databaseBuilder(
            context.applicationContext,
            DevProfileDB::class.java,
            "dev.db"
        ).build()
    }

    @Provides
    fun provideDevProfileDao(database: DevProfileDB) = database.devProfileDao()

    @Provides
    fun provideSkillsDao(database: DevProfileDB) = database.getSkillsDao()

    @Provides
    fun provideDevProfileRef() = Firebase.firestore.collection(DEV_PROFILE_REF)

    @Provides
    @Named(SKILLS_REF)
    fun provideSkillsRef() = Firebase.firestore.collection(SKILLS_REF)

    @Provides
    fun provideDevProfileRepository(
        devProfileRef: CollectionReference,
        @Named(SKILLS_REF) skillsRef: CollectionReference,
        localDevProfileDataSource: DevProfileDao,
        localSkillsDataSource: SkillsDao
    ): DevProfileRepository = DevProfileRepositoryImpl(
        devProfileRef,
        skillsRef,
        localDevProfileDataSource,
        localSkillsDataSource
    )

    @Provides
    fun provideUseCases(
        repo: DevProfileRepository
    ) = UseCases(
        getDevProfile = GetDevProfile(repo),
        getLocalDevProfile = GetLocalDevProfile(repo),
        getSkills = GetSkills(repo),
        getLocalSkills = GetLocalSkills(repo)
    )
}