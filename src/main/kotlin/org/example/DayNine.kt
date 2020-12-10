package org.example

import kotlinx.coroutines.runBlocking
import org.example.aocutil.Day

class DayNine: Day {
    private lateinit var data: Array<Long>

    override fun preprocess() {
        data = runBlocking<String> { return@runBlocking collect(9) }
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { it.toLong() }
            .toTypedArray()

    }

    private fun occursOnce(idx: Int): Boolean {
        val nums = data.slice((idx - 25) until idx)
        val n = data[idx]
        for (i in nums) {
            for (j in nums) {
                if (i == j)
                    continue
                if (i + j == n)
                    return true
            }
        }
        return false
    }

    private fun findNum(idx: Int): Long? {
        val n = data[idx]
        var index = 0
        val nums = mutableListOf<Long>()
        while (true) {
            for (i in index until data.size) {
                nums.add(data[i])
                if (nums.size < 2) continue
                val sum = nums.reduce { a, l -> a + l }
                if (sum == n)
                    return nums.minOrNull()!! + nums.maxOrNull()!!
                if (sum > n)
                    break
            }
            index++
            if (index == data.size)
                return null
            nums.clear()
        }
    }

    override fun partOne() {
        var idx = 25

        while (idx < data.size) {
            if (!occursOnce(idx)) {
                println("Invalid number: ${data[idx]}")
                break
            }
            idx++
        }
    }

    override fun partTwo() {
        var idx = 25
        while (idx < data.size) {
            if (!occursOnce(idx)) {
                println("Found: ${findNum(idx)}")
                break
            }
            idx++
        }
    }
}