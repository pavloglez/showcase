package glez.pavlo.showcase.feature_dev_profile.domain.model

sealed class Result<out T> {
    object Loading : Result<Nothing>()

    data class Success<out T>(val data: T) : Result<T>()

    data class Failure(val e: Exception?) : Result<Nothing>()
}