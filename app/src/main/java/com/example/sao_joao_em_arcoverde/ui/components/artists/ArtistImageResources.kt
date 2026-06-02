package com.example.sao_joao_em_arcoverde.ui.components.artists

import android.content.Context
import androidx.annotation.DrawableRes

@DrawableRes
fun Context.artistImageResId(artistId: String?): Int? {
    if (artistId.isNullOrBlank()) {
        return null
    }

    val resourceId = resources.getIdentifier(
        artistId,
        "drawable",
        packageName
    )

    return if (resourceId != 0) {
        resourceId
    } else {
        null
    }
}