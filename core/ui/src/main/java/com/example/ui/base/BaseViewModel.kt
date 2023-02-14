package com.example.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<Action, State> constructor(initialState: State) : ViewModel() {

    private val _snackbarFlow = MutableSharedFlow<String>()
    val snackbarFlow = _snackbarFlow.asSharedFlow()

    private val state = MutableStateFlow(initialState)
    val uiState = state
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            initialState
        )

    fun submitAction(action: Action) {
        onAction(action)
    }

    protected abstract fun onAction(action: Action)

    protected fun showSnackbar(value: String) {
        viewModelScope.launch {
            _snackbarFlow.emit(value)
        }
    }

    private suspend fun <R> runCatching(block: suspend () -> R): Result<R> {
        return try {
            Result.success(block())
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    private suspend fun <R> makeSuspendCall(block: suspend () -> R): Result<R> {
        return withContext(Dispatchers.IO) {
            runCatching(block)
        }
    }

    /**
     * It is used for calls that return a value
     */
    protected fun <R> makeSuspendCall2(
        block: suspend () -> R,
        onSuccess: ((R) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null,
        onLoading: ((Boolean) -> Unit)? = null,
        suspendJob: ((Job) -> Unit)? = null,
        dispatcher: CoroutineContext = Dispatchers.IO,
        showSnackbarOnError: Boolean = true
    ) {
        viewModelScope.launch(dispatcher) {
            onLoading?.invoke(true)
            try {
                val result = async { block() }
                onSuccess?.invoke(result.await())
            } catch (e: Exception) {
                if (showSnackbarOnError) {
                    e.message?.let { errorMessage ->
                        showSnackbar(errorMessage)
                    }
                }
                onError?.invoke(e)
                e.printStackTrace()
            } finally {
                onLoading?.invoke(false)
            }
        }.apply {
            suspendJob?.invoke(this)
        }
    }

    /**
     * It is used for calls that don't return a value
     */
    protected fun <R> makeSuspendCall2(
        block: suspend () -> Unit,
        onSuccess: (() -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null,
        onLoading: ((Boolean) -> Unit)? = null,
        suspendJob: ((Job) -> Unit)? = null,
        dispatcher: CoroutineContext = Dispatchers.IO,
        showSnackbarOnError: Boolean = true
    ) {
        viewModelScope.launch(dispatcher) {
            onLoading?.invoke(true)
            try {
                block()
                onSuccess?.invoke()
            } catch (e: Exception) {
                if (showSnackbarOnError) {
                    e.message?.let { errorMessage ->
                        showSnackbar(errorMessage)
                    }
                }
                onError?.invoke(e)
                e.printStackTrace()
            } finally {
                onLoading?.invoke(false)
            }
        }.apply {
            suspendJob?.invoke(this)
        }
    }

    protected fun <R> makeSuspendCall(
        block: suspend () -> R,
        onSuccess: ((R) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null,
        onLoading: ((Boolean) -> Unit)? = null,
        suspendJob: ((Job) -> Unit)? = null,
        showSnackbarOnError: Boolean = true
    ) {
        viewModelScope.launch {

            onLoading?.invoke(true)
            makeSuspendCall(block).apply {
                try {
                    val result = getOrThrow()
                    onSuccess?.invoke(result)
                } catch (e: IOException) {
                    if (showSnackbarOnError) showSnackbar("Internet connection problem")
                    onError?.invoke(Exception("Internet connection problem", e.cause))
                    e.printStackTrace()
                } catch (e: Exception) {
                    if (showSnackbarOnError) {
                        e.message?.let { errorMessage ->
                            showSnackbar(errorMessage)
                        }
                    }
                    onError?.invoke(e)
                    e.printStackTrace()
                } finally {
                    onLoading?.invoke(false)
                }
            }
        }.apply {
            suspendJob?.invoke(this)
        }
    }

    protected fun updateState(newState: State.(State) -> State) {
        state.apply {
            update { value.newState(it) }
        }
    }

    protected suspend fun updateStateWithDelay(
        delay: Long,
        newState: State.(State) -> State
    ) {
        delay(delay)
        state.apply {
            update { value.newState(it) }
        }
    }

}
