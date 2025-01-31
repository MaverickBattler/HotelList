package com.mokhnatkinkirill.hotellist.details.presentation.composable

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.compose.ui.graphics.Color


fun getColorFromAttr(@AttrRes attr: Int, context: Context): Color {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(attr, typedValue, true)
    return Color(typedValue.data)
}