package ir.reza_mahmoudi.udemika.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.reza_mahmoudi.udemika.data.repository.Repository
import ir.reza_mahmoudi.udemika.model.Comment
import ir.reza_mahmoudi.udemika.utils.MainPagingSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PagerModule {
    @Singleton
    @Provides
    fun providePagingConfig(): PagingConfig {
        return PagingConfig(pageSize = 5, enablePlaceholders = false, initialLoadSize = 10)
    }
}