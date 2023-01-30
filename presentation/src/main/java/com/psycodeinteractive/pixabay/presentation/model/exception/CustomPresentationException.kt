package com.psycodeinteractive.pixabay.presentation.model.exception

import android.content.res.Resources
import com.psycodeinteractive.pixabay.presentation.R

class CustomPresentationException(
    override val message: String
) : PresentationException(
    titleResourceId = R.string.unknown_exception_title
) {
    override fun getMessageText(resources: Resources) = message
}
