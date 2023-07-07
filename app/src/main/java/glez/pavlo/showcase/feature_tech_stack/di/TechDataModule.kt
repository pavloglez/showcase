package glez.pavlo.showcase.feature_tech_stack.di

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
import glez.pavlo.showcase.core.Constants.TECH_STACK_REF
import glez.pavlo.showcase.feature_tech_stack.data.local.TechDao
import glez.pavlo.showcase.feature_tech_stack.data.local.TechStackDB
import glez.pavlo.showcase.feature_tech_stack.data.repository.TechStackRepositoryImpl
import glez.pavlo.showcase.feature_tech_stack.domain.repository.TechStackRepository
import glez.pavlo.showcase.feature_tech_stack.domain.use_case.GetLocalTechStack
import glez.pavlo.showcase.feature_tech_stack.domain.use_case.GetTechStack
import glez.pavlo.showcase.feature_tech_stack.domain.use_case.TechUseCases
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TechDataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TechStackDB {
        return Room.databaseBuilder(
            context.applicationContext,
            TechStackDB::class.java,
            "tech.db"
        ).build()
    }


    @Provides
    fun provideTechDao(database: TechStackDB) = database.techDao()

    @Provides
    @Named(TECH_STACK_REF)
    fun provideTechStackFBRef() = Firebase.firestore.collection(TECH_STACK_REF)

    @Provides
    fun provideTechStackRepository(
        @Named(TECH_STACK_REF) techStackRef: CollectionReference,
        localDataSource: TechDao
    ): TechStackRepository = TechStackRepositoryImpl(techStackRef, localDataSource)

    @Provides
    fun provideUseCases(
        repo: TechStackRepository
    ) = TechUseCases(
        getTechStack = GetTechStack(repo),
        getLocalTechStack = GetLocalTechStack(repo)
    )
}

