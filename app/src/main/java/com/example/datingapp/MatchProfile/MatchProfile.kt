package com.example.datingapp.MatchProfile

import androidx.annotation.DrawableRes
import com.example.datingapp.R

data class MatchProfile(
    val name: String,
    @DrawableRes val drawableResId: Int,
)

val profiles = listOf(
    MatchProfile("Erlich Bachman", R.drawable.model),
    MatchProfile("Richard Hendricks", R.drawable.model),
    MatchProfile("Laurie Bream", R.drawable.model),
    MatchProfile("Russ Hanneman", R.drawable.model),
    MatchProfile("Dinesh Chugtai", R.drawable.model),
    MatchProfile("Monica Hall", R.drawable.model),
    MatchProfile("Bertram Gilfoyle", R.drawable.model),
)