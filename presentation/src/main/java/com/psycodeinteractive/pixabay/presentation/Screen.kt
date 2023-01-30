package com.psycodeinteractive.pixabay.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.psycodeinteractive.pixabay.presentation.feature.imagelist.ImageListEvent
import com.psycodeinteractive.pixabay.presentation.model.exception.PresentationException
import com.psycodeinteractive.pixabay.presentation.widget.ErrorDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
inline fun <reified VM : BaseViewModel<VS, E>, reified VS : ViewState, reified E : Event> Screen(
    viewModel: VM = hiltViewModel(),
    crossinline children: @Composable (viewModel: VM, viewState: VS) -> Unit
) {
    val viewState by viewModel.collectViewState()
    OnLifecycle(minActiveState = CREATED) {
        viewModel.onViewCreated()
    }

    HandleErrors(viewModel)

    children(viewModel, viewState)
}

@Composable
inline fun <reified VM : BaseViewModel<VS, E>, reified VS : ViewState, reified E : Event> HandleErrors(
    viewModel: VM
) {
    val resources = LocalContext.current.resources
    var presentationException by remember { mutableStateOf<PresentationException?>(null) }
    presentationException?.run {
        ErrorDialog(
            title = getTitleText(resources),
            message = getMessageText(resources)
        ) { showDialog ->
            if (!showDialog) {
                presentationException = null
            }
        }
    }
    viewModel.exceptionFlow.observeWithLifecycle { exception ->
        presentationException = exception
    }
}

@Composable
inline fun <VM : BaseViewModel<VS, out Event>, reified VS : ViewState>
VM.collectViewState(): MutableState<VS> {
    val composeState = viewState.collectAsState(initialViewState.wrap())
    val newState = remember { mutableStateOf(initialViewState) }
    newState.value = composeState.value.state
    return newState
}

@Composable
inline fun <reified Model> Flow<Model>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = STARTED,
    noinline action: suspend (Model) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { model ->
            action(model)
        }
    }
}

@Composable
inline fun OnLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = STARTED,
    crossinline action: suspend () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        with(lifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(minActiveState) {
                    action()
                }
            }
        }
    }
}
