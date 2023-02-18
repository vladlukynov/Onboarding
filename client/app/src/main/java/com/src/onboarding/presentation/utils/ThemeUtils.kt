package com.src.onboarding.presentation.utils

class ThemeUtils {
    fun convertCountOfTheme(count: Int): String {
        if (count % 10 == 1 && count % 100 != 11) {
            return "$count тема"
        }
        if (count % 10 in 2..4 && (count % 100 < 10 || count % 100 > 19)) {
            return "$count темы"
        }
        return "$count тем"
    }
}