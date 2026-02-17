package com.example.jikananimeexplorer.utils.result


import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Resource<T> {

    return try {

        Resource.Success(apiCall())

    } catch (e: HttpException) {

        Resource.Error(
            message = "Server error: ${e.message()}",
            throwable = e
        )

    } catch (e: IOException) {

        Resource.Error(
            message = "Network error. Check connection.",
            throwable = e
        )

    } catch (e: Exception) {

        Resource.Error(
            message = "Unknown error occurred",
            throwable = e
        )
    }
}
