package com.psycodeinteractive.pixabay.presentation.feature.splash

import com.psycodeinteractive.pixabay.domain.Logger
import com.psycodeinteractive.pixabay.presentation.BaseViewModel
import com.psycodeinteractive.pixabay.presentation.feature.imagelist.ImageListPresentationDestination
import com.psycodeinteractive.pixabay.presentation.feature.splash.SplashEvent.StartSplash
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    override val logger: Logger
) : BaseViewModel<SplashViewState, SplashEvent>() {

    override val initialViewState = SplashViewState

    override fun onViewCreated() {
        StartSplash.dispatchEvent()
    }

    fun onSplashFinished() {
        ImageListPresentationDestination.navigate()
    }
}
