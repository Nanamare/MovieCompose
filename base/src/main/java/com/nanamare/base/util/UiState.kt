package com.nanamare.base.util

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class UiState<out R> {

    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val exception: Exception) : UiState<Nothing>()
    object Loading : UiState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [UiState] is of type [Success] & holds non-null [Success.data].
 */
val UiState<*>.succeeded
    get() = this is UiState.Success && data != null

fun <T> UiState<T>.successOr(fallback: T): T {
    return (this as? UiState.Success<T>)?.data ?: fallback
}

val <T> UiState<T>.data: T?
    get() = (this as? UiState.Success)?.data

/**
 * Updates value of [liveData] if [UiState] is of type [Success]
 */
inline fun <reified T> UiState<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
    if (this is UiState.Success) {
        liveData.value = data
    }
}

/**
 * Updates value of [MutableStateFlow] if [UiState] is of type [Success]
 */
inline fun <reified T> UiState<T>.updateOnSuccess(stateFlow: MutableStateFlow<T>) {
    if (this is UiState.Success) {
        stateFlow.value = data
    }
}

/**
 * Change type of Result to Ui State
 */
fun <T> Result<T>.toUiState(): UiState<T> {
    return when {
        isFailure -> UiState.Error(exception = Exception(exceptionOrNull()))
        isSuccess -> UiState.Success(data = getOrThrow())
        else -> UiState.Loading
    }
}