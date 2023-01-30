package com.psycodeinteractive.pixabay.presentation.model.exception

import android.content.res.Resources
import android.view.View
import androidx.annotation.StringRes

abstract class PresentationException(
    val throwable: Throwable = Throwable(),
    @StringRes
    private val titleResourceId: Int = View.NO_ID,
    @StringRes
    private val messageResourceId: Int = View.NO_ID
) : Exception(throwable) {
    open fun getTitleText(resources: Resources) = resources.getString(titleResourceId)
    open fun getMessageText(resources: Resources) = resources.getString(messageResourceId)
}
