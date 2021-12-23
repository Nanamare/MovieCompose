package com.nanamare.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class NonParamCoroutineUseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     */
    suspend operator fun invoke(): Result<R> {
        return try {
            // Moving all use case's executions to the injected dispatcher
            // In production code, this is usually the Default dispatcher (background thread)
            // In tests, this becomes a TestCoroutineDispatcher
            withContext(coroutineDispatcher) {
                execute().let {
                    Result.success(it)
                }
            }
        } catch (e: Exception) {
            Logger.getLogger(NonParamCoroutineUseCase::class.java.simpleName)
                .log(Level.WARNING, "throw error", e)
            Result.failure(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R
}
