package com.example.myweatherapp.presentation.ui

import androidx.compose.ui.graphics.Color
import com.example.myweatherapp.presentation.theme.DarkBlue
import com.example.myweatherapp.presentation.theme.DefaultColor

data class Style(
    val backgroundColor: Color = DarkBlue,
    val textColor: Color = Color.White,
    val iconTint: Color = Color.White,
    val contentColor: Color = DefaultColor
)
