package com.codereview.iou.util

import java.security.SecureRandom

fun getRandomValue(min: Int, max: Int) = SecureRandom().nextInt(max - min + 1) + min

fun getRandomValue(min: Long, max: Long) = min + ((SecureRandom().nextLong() shl 1) ushr 1) % (max - min + 1)

fun getRandomValue(min: Double, max: Double) = min + (max - min + 1) * SecureRandom().nextDouble()