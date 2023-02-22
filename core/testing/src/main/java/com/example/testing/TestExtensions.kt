package com.example.testing

import org.junit.jupiter.api.Assertions.assertEquals

infix fun <T> T.shouldBeEqualTo(expected: T) = this.apply { assertEquals(expected, this) }