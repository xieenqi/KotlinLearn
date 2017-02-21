package com.mykotlin.utils

/**
 * Created by qiqi on 16/12/6.
 */
data class Coordinate(val x: Double, val y: Double) {

    val Coordinate.theta: Double
        get() {
            return Math.atan(y / x)
        }

    fun Coordinate.R(): Double {
        return Math.hypot(x, y)
    }
}