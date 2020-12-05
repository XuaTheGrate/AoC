package org.example

import kotlinx.coroutines.runBlocking
import org.example.aocutil.Day

class DayFive: Day {
    private lateinit var passes: Array<String>

    override fun preprocess() {
        passes = runBlocking<String> { return@runBlocking collect(5) }
                .split("\n")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .toTypedArray()
    }

    private fun getResult() = passes.map { pass -> // string
        var rowRange = (0 until 128).toList()
        for ((idx, char) in pass.withIndex()) {
            if (idx == 7)
                break
            rowRange = when (char) {
                'F' -> rowRange.slice(0 until (rowRange.size / 2))
                'B' -> rowRange.slice((rowRange.size / 2) until rowRange.size)
                else -> throw AssertionError()
            }
        }
        assert(rowRange.size == 1)
        val row = rowRange[0]
        var colRange = (0 until 8).toList()
        for (char in pass.substring((pass.length - 3) until (pass.length-1))) {
            colRange = when (char) {
                'L' -> colRange.slice(0 until (colRange.size / 2))
                'R' -> colRange.slice((colRange.size / 2) until colRange.size)
                else -> throw AssertionError()
            }
        }
        assert(colRange.size == 2)
        val col = if (pass.last() == 'L')
            colRange.first()
        else
            colRange.last()
        val seatID = (row * 8) + col
        seatID
    }.toIntArray()

    override fun partOne() {
        val result = getResult()
        println("Highest ID=${result.maxOrNull()}")
    }

    override fun partTwo() {
        val data = getResult()
        val nums = (0..(128 * 8))
                .filter {
                    it !in data
                }
                .toIntArray()
        nums.forEach { seat ->
            if (seat - 1 in data && seat + 1 in data) {
                println("Your seat is: $seat")
                return@forEach
            }
        }
    }
}