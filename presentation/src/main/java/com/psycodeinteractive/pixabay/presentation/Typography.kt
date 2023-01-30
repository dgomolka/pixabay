package com.psycodeinteractive.pixabay.presentation

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.sp

val regular = Font(R.font.poppins_regular, Normal)
val medium = Font(R.font.poppins_medium, Medium)
val semibold = Font(R.font.poppins_semibold, SemiBold)
val bold = Font(R.font.poppins_bold, Bold)

val appFontFamily = FontFamily(regular, medium, semibold, bold)

private val textStyleMediumMap = mutableMapOf<TextStyle, TextStyle>()
private val textStyleSemiBoldMap = mutableMapOf<TextStyle, TextStyle>()
private val textStyleBoldMap = mutableMapOf<TextStyle, TextStyle>()

private val commonTextStyle = TextStyle(fontFamily = appFontFamily)
val themeTypography = Typography(
    h4 = commonTextStyle.copy(
        fontSize = 30.sp
    ),
    h5 = commonTextStyle.copy(
        fontSize = 24.sp
    ),
    h6 = commonTextStyle.copy(
        fontSize = 18.sp
    ),
    subtitle1 = commonTextStyle.copy(
        fontSize = 16.sp
    ),
    subtitle2 = commonTextStyle.copy(
        fontSize = 12.sp
    ),
    body1 = commonTextStyle.copy(
        fontSize = 16.sp
    ),
    body2 = commonTextStyle.copy(
        fontSize = 15.sp
    ),
    button = commonTextStyle.copy(
        fontSize = 14.sp
    ),
    caption = commonTextStyle.copy(
        fontSize = 9.5.sp
    ),
    overline = commonTextStyle.copy(
        fontSize = 8.sp
    )
)

fun TextStyle.semiBold() = retrieveTextStyle(this, textStyleSemiBoldMap, SemiBold)
fun TextStyle.bold() = retrieveTextStyle(this, textStyleBoldMap, Bold)
fun TextStyle.medium() = retrieveTextStyle(this, textStyleMediumMap, Medium)

private fun retrieveTextStyle(
    textStyle: TextStyle,
    textStyleMap: MutableMap<TextStyle, TextStyle>,
    fontWeight: FontWeight
) = textStyleMap[textStyle] ?: textStyle.copy(fontWeight = fontWeight).apply {
    textStyleMap[textStyle] = this
}
