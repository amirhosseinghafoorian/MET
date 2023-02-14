package com.example.common

inline fun <T> Iterable<T>.firstONull(predicate: (T) -> Boolean): T? {
    for (element in this) if (predicate(element)) return element
    return null
}