package org.example

import kotlinx.coroutines.runBlocking
import org.example.aocutil.Cache
import org.example.aocutil.Day
import kotlin.math.absoluteValue

class DayTen: Day {
    private lateinit var jolts: Array<Int>
    private val cache = Cache()

    override fun preprocess() {
        jolts = runBlocking<String> { return@runBlocking collect(10) }
        /*jolts = """
            16
            10
            15
            5
            1
            11
            7
            19
            6
            12
            4
        """*/
            .trimIndent()
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .toTypedArray()
            .sortedArray()
    }

    override fun partOne() {
        val jolts = this.jolts.toMutableList()
        var rating = 0
        val differences = mutableListOf(0L, 0L, 0L)
        while (true) {
            if (jolts.isEmpty())
                break
            // println(jolts)
            val pick = jolts.first { it in (rating .. rating+3) }
            jolts.remove(pick)
            val diff = (rating - pick).absoluteValue
            differences[diff-1]++
            rating = pick
        }
        differences[2]++
        println("Total voltage: ${differences[0]}, ${differences[2]} (${differences[0] * differences[2]})")
    }

    override fun partTwo() {
        // not possible in java, use this python code instead
        /* credit: https://repl.it/@mcparadip/aoc-10
from functools import lru_cache

with open("day-10.txt") as f:
    nums = sorted(int(x) for x in f)

target = max(nums) + 3
nums = [0] + nums + [target]

@lru_cache(maxsize=None)
def solve(i=0):
    if i == len(nums) - 1:
        return 1

    count = 0
    for j, x in enumerate(nums[i + 1:], i + 1):
        if x - nums[i] > 3: break
        count += solve(j)

    return count

print(solve())
         */
        println("Gave up, use python because java sucks")
    }
}