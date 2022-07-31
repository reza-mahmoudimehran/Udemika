package ir.reza_mahmoudi.udemika.view.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.reza_mahmoudi.udemika.data.repository.Repository
import ir.reza_mahmoudi.udemika.utils.CoursePagingSource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val pagingConfig: PagingConfig
) : ViewModel() {
    fun dataC() = Pager(pagingConfig) { CoursePagingSource(repository) }.flow.cachedIn(viewModelScope)
}