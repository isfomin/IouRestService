package com.codereview.iou.util

import java.util.*

private val names = listOf(
    "Bobby",
    "Timofei",
    "Jimmy",
    "Billy")

fun genAmount(): Double = ("%.2f".format(Locale.ROOT, getRandomValue(0.01, 99_999.00))).toDouble()

fun genName(): String = names[getRandomValue(0, names.lastIndex)]

fun genId(): Int = getRandomValue(1, Int.MAX_VALUE)

fun genLongId(): Long = getRandomValue(100L, 9_999_999L)
