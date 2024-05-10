package com.mshdabiola.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.mshdabiola.data.model.asExternalImage
import com.mshdabiola.database.dao.ImageDao
import com.mshdabiola.database.model.ImageEntity
import com.mshdabiola.model.Image
import com.mshdabiola.network.INetworkDataSource
import com.mshdabiola.network.model.Imageinfo
import java.io.IOException
import java.net.http.HttpConnectTimeoutException
import kotlin.random.Random
import kotlin.random.nextInt

@OptIn(ExperimentalPagingApi::class)
class ModeRemoteMediator(
    private val iNetworkDataSource: INetworkDataSource,
    private val iImageDao: ImageDao,
) : RemoteMediator<Int, ImageEntity>() {

    var key2: String? = null
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>,
    ): MediatorResult {
        return try {
            // The network load method takes an optional String
            // parameter. For every page after the first, pass the String
            // token returned from the previous page to let it continue
            // from where it left off. For REFRESH, pass null to load the
            // first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true,
                )
                // Query remoteKeyDao for the next RemoteKey.
                LoadType.APPEND -> {
//                    val remoteKey = database.withTransaction {
//                        remoteKeyDao.remoteKeyByQuery(query)
//                    }

                    // You must explicitly check if the page key is null when
                    // appending, since null is only valid for initial load.
                    // If you receive null for APPEND, that means you have
                    // reached the end of pagination and there are no more
                    // items to load.

                    key2
                }
            }

            // Suspending network load via Retrofit. This doesn't need to
            // be wrapped in a withContext(Dispatcher.IO) { ... } block
            // since Retrofit's Coroutine CallAdapter dispatches on a
            // worker thread.

            val response =
                iNetworkDataSource.getTimeline(state.config.pageSize, loadKey ?: getKey())

            key2 = response.continueX?.grncontinue ?: getKey()
            val maps = response.query?.pages?.mapNotNull { page ->
                page?.imageinfo?.mapNotNull { it?.toModel() }
            }

            if (loadType == LoadType.REFRESH) {
                iImageDao.clearAll()
            }

            // Insert new users into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            maps
                ?.flatten()
                ?.map { it.asExternalImage() }
                ?.let {
                    iImageDao.insertAll(it)
                }

            MediatorResult.Success(
                endOfPaginationReached = false,
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpConnectTimeoutException) {
            MediatorResult.Error(e)
        }

//        return try {
//            // The network load method takes an optional after=<user.id>
//            // parameter. For every page after the first, pass the last user
//            // ID to let it continue from where it left off. For REFRESH,
//            // pass null to load the first page.
//            val loadKey = when (loadType) {
//                LoadType.REFRESH -> null
//                // In this example, you never need to prepend, since REFRESH
//                // will always load the first page in the list. Immediately
//                // return, reporting end of pagination.
//                LoadType.PREPEND ->
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//
//                    // You must explicitly check if the last item is null when
//                    // appending, since passing null to networkService is only
//                    // valid for initial load. If lastItem is null it means no
//                    // items were loaded after the initial REFRESH and there are
//                    // no more items to load.
//                    if (lastItem == null) {
//                        return MediatorResult.Success(
//                            endOfPaginationReached = true
//                        )
//                    }
//
//                    lastItem.id
//                }
//            }
//
//            // Suspending network load via Retrofit. This doesn't need to be
//            // wrapped in a withContext(Dispatcher.IO) { ... } block since
//            // Retrofit's Coroutine CallAdapter dispatches on a worker
//            // thread.
//            val next=getKey()
//
//            val response = iNetworkDataSource.getTimeline(4, next)
//
//            val maps=response.query?.pages?.mapNotNull { page ->
//                page?.imageinfo?.mapNotNull { it?.toModel() }
//            }
//
//            if (loadType == LoadType.REFRESH) {
//                   iImageDao.clear()
//                }
//
//                // Insert new users into database, which invalidates the
//                // current PagingData, allowing Paging to present the updates
//                // in the DB.
//            maps?.flatten()?.forEach {
//                iImageDao.insert(it.copy(id = it.id+random.nextULong()))
//            }
//
//
//
//            MediatorResult.Success(
//                endOfPaginationReached = false
//            )
//        } catch (e: IOException) {
//            MediatorResult.Error(e)
//        } catch (e: HttpConnectTimeoutException) {
//            MediatorResult.Error(e)
//        }
    }

    val random = Random(4)
    fun getKey(): String {
        val num = random.nextInt(10, 99)
        val number = random.nextInt(10..99)
        return "0.5739937985$num|0.57399474331|620566$number|0"
    }
}

fun Imageinfo.toModel() = Image(
    id = id ?: "",
    user = user ?: "",
    userid = userid ?: 4,
    url = url ?: "",
    timestamp = timestamp ?: "",
    mime = mime ?: "",
    mediaType = mediatype ?: "",
    descriptionUrl = descriptionurl ?: "",
    descriptionShortUrl = descriptionshorturl ?: "",
)
