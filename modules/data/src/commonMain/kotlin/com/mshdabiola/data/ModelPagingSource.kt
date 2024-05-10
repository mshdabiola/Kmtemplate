package com.mshdabiola.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mshdabiola.model.Image
import com.mshdabiola.network.INetworkDataSource
import kotlin.random.Random
import kotlin.random.nextInt

class ModelPagingSource(
    private val iNetworkDataSource: INetworkDataSource,
) : PagingSource<String, Image>() {
    override fun getRefreshKey(state: PagingState<String, Image>): String? {
        return getKey()
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Image> {
        return try {
            // Start refresh at page 1 if undefined.
            val next = params.key ?: getKey()
            val response = iNetworkDataSource.getTimeline(4, next)
            val maps = response.query?.pages?.mapNotNull { page ->
                page?.imageinfo?.mapNotNull { it?.toModel() }
            }
            LoadResult.Page(
                data = maps?.flatten() ?: emptyList(),
                prevKey = null, // Only paging forward.
                nextKey = getKey(), // response.continueX?.grncontinue ?: getKey()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
        }
    }

    val random = Random(4)
    fun getKey(): String {
        val num = random.nextInt(10, 99)
        val number = random.nextInt(10..99)
        return "0.5739937985$num|0.57399474331|620566$number|0"
    }
}
