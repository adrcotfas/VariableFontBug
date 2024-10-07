package com.example.variablefontbug.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.variablefontbug.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

@OptIn(ExperimentalTextApi::class)
fun fontWith(resId: Int, weight: Int): FontFamily {
    return FontFamily(
        Font(
            resId = resId,
            weight = FontWeight(weight),
            variationSettings = FontVariation.Settings((FontVariation.weight(weight)))
        )
    )
}

val fontWeights = listOf(100, 200, 300, 400)
val azeretMap =
    fontWeights.associateWith { weight -> fontWith(R.font.azeret_mono, weight) }

val robotoMap =
    fontWeights.associateWith { weight -> fontWith(R.font.roboto_mono, weight) }

val textStyles = mapOf(
    TextFont.AZERET.ordinal to azeretMap,
    TextFont.ROBOTO.ordinal to robotoMap
)

enum class TextFont {
    AZERET, ROBOTO
}