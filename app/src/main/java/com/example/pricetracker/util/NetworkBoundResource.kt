package com.example.pricetracker.util

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    emit(Result.Loading)
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Result.Loading)

        try {
            val fetchedResult = fetch()
            saveFetchResult(fetchedResult)
            query().map { Result.Success(it) }
        } catch (t: Throwable) {
            onFetchFailed(t)
            query().map {
                Result.Success(it)
                Result.Error("Couldn't reach server. It might be down")
            }
        }
    } else {
        query().map { Result.Success(it)  }
    }
    emitAll(flow)
}