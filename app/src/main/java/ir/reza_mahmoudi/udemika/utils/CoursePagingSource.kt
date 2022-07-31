package ir.reza_mahmoudi.udemika.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.reza_mahmoudi.udemika.data.repository.Repository
import ir.reza_mahmoudi.udemika.model.Course
import kotlinx.coroutines.delay

class CoursePagingSource(
    private val repository: Repository
) : PagingSource<Int, Course>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Course> {
        val page = params.key ?: 0

        return try {
            val entities = repository.local
                .getCoursesListPage(params.loadSize, page * params.loadSize)

            // simulate page loading
            if (page != 0) delay(500)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Course>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}