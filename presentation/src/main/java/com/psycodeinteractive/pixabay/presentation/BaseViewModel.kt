package com.psycodeinteractive.pixabay.presentation

import androidx.lifecycle.ViewModel
import com.psycodeinteractive.pixabay.domain.Logger
import com.psycodeinteractive.pixabay.presentation.model.exception.CustomPresentationException
import com.psycodeinteractive.pixabay.presentation.model.exception.PresentationException
import com.psycodeinteractive.pixabay.presentation.navigation.BackDestination
import com.psycodeinteractive.pixabay.presentation.navigation.PresentationDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<BaseState : ViewState, BaseEvent : Event> : ViewModel() {

    abstract val logger: Logger

    private val _viewState by lazy { MutableStateFlow(initialViewState.wrap()) }
    val viewState by lazy { _viewState.asStateFlow() }

    private val _eventChannel by lazy { Channel<BaseEvent>(BUFFERED) }
    val eventFlow by lazy { _eventChannel.receiveAsFlow() }

    private val _navigationChannel by lazy { Channel<PresentationDestination>(BUFFERED) }
    val navigationFlow by lazy { _navigationChannel.receiveAsFlow() }

    private val _exceptionChannel by lazy { Channel<PresentationException>(BUFFERED) }
    val exceptionFlow by lazy { _exceptionChannel.receiveAsFlow() }

    abstract val initialViewState: BaseState

    protected fun updateViewState(mutation: BaseState.() -> Unit) {
        val viewState = _viewState.value.state
        mutation(viewState)
        _viewState.value = viewState.wrap()
    }

    protected fun BaseEvent.dispatchEvent() {
        _eventChannel.trySend(this@dispatchEvent).handleChannelException()
    }

    protected fun PresentationDestination.navigate() {
        _navigationChannel.trySend(this@navigate).handleChannelException()
    }

    fun currentViewState() = viewState.value.state

    protected fun notifyError(exception: PresentationException) {
        logger.e(exception)
        _exceptionChannel.trySend(exception).handleChannelException()
    }

    private fun ChannelResult<Any>.handleChannelException() = onFailure { exceptionChannelException ->
        exceptionChannelException?.let(logger::e)
    }

    open fun onViewCreated() {}

    fun onCloseAction() {
        BackDestination.navigate()
    }

    fun onError(message: String) {
        notifyError(CustomPresentationException(message))
    }

    fun BaseState.wrap() = StateWrapper(this)

    class StateWrapper<BaseState : ViewState>(val state: BaseState)
}
