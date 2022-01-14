package com.sliide.test.core

enum class Gender {
    MALE,
    FEMALE
}

fun Gender.toCamelCase(): String {
    return this.name.lowercase().capitalize()
}
