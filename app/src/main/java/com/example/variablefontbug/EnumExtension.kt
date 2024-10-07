package com.example.variablefontbug

inline fun <reified T : Enum<T>> T.prettyName(): String {
    return name.lowercase().replaceFirstChar { it.uppercase() }
}

inline fun <reified T : Enum<T>> prettyNames(): List<String> {
    return enumValues<T>().map { it.prettyName() }
}