package com.psycodeinteractive.pixabay.presentation.feature.splash

import com.psycodeinteractive.pixabay.presentation.Event

sealed class SplashEvent : Event {
    object StartSplash : SplashEvent()
}
