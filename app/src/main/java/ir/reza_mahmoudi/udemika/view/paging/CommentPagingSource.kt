package ir.reza_mahmoudi.udemika.view.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.reza_mahmoudi.udemika.data.repository.IRepository
import ir.reza_mahmoudi.udemika.model.Comment
import kotlinx.coroutines.delay

class MainPagingSource(
    private val repository: IRepository,
    private val courseId: Long
) : PagingSource<Int, Comment>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> {
        val page = params.key ?: 0

        return try {
            val entities = repository.getComments(courseId,params.loadSize, page * params.loadSize)

            // simulate page loading
            if (page != 0) delay(1000)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Comment>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}