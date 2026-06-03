package com.example.sao_joao_em_arcoverde.data.preferences

enum class ThemeMode(
    val storageValue: String,
    val label: String
) {
    SYSTEM(
        storageValue = "system",
        label = "Sistema"
    ),
    LIGHT(
        storageValue = "light",
        label = "Claro"
    ),
    DARK(
        storageValue = "dark",
        label = "Escuro"
    );

    companion object {
        fun fromStorageValue(value: String?): ThemeMode {
            return entries.firstOrNull { it.storageValue == value } ?: SYSTEM
        }
    }
}