package ir.reza_mahmoudi.udemika.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.reza_mahmoudi.udemika.data.local.CoursesDao
import ir.reza_mahmoudi.udemika.data.remote.CoursesApiHelper
import ir.reza_mahmoudi.udemika.data.repository.IRepository
import ir.reza_mahmoudi.udemika.data.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: CoursesApiHelper,
        localDataSource: CoursesDao
    ) = Repository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideINewsRepository(repository: Repository): IRepository = repository
}