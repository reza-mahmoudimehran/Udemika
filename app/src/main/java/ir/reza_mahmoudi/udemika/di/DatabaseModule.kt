package ir.reza_mahmoudi.udemika.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.reza_mahmoudi.udemika.data.local.CoursesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = CoursesDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideDao(database: CoursesDatabase) = database.getMoviesDao()

}