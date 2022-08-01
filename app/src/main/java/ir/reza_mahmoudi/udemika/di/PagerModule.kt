package ir.reza_mahmoudi.udemika.di

import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagerModule {
    @Singleton
    @Provides
    fun providePagingConfig(): PagingConfig {
        return PagingConfig(pageSize = 3, enablePlaceholders = false, initialLoadSize = 3)
    }
}