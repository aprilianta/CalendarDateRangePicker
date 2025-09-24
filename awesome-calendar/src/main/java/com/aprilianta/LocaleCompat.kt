package com.aprilianta

import android.os.Build
import java.util.Locale

object LocaleCompat {
    fun forLanguageTagCompat(tag: String): Locale {
        val cleaned = tag.trim().replace('_', '-')
        if (cleaned.isEmpty()) return Locale.getDefault()

        if (Build.VERSION.SDK_INT >= 21) {
            return Locale.forLanguageTag(cleaned)
        }

        // Pre-21: parse manual -> language[-country[-variant…]]
        val parts = cleaned.split('-')
        var language = parts.getOrNull(0)?.lowercase(Locale.ROOT).orEmpty()
        language = when (language) {
            "iw" -> "he" // Hebrew
            "ji" -> "yi" // Yiddish
            "in" -> "id" // Indonesian
            else -> language
        }
        val country = parts.getOrNull(1)?.uppercase(Locale.ROOT).orEmpty()
        val variant = if (parts.size > 2) parts.subList(2, parts.size).joinToString("_") else ""

        return when {
            variant.isNotEmpty() -> Locale(language, country, variant)
            country.isNotEmpty() -> Locale(language, country)
            else -> Locale(language)
        }
    }
}