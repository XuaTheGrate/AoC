package org.example

import kotlinx.coroutines.runBlocking
import org.example.aocutil.Day

class DayThree: Day {
    private class Position(var x: Int, var y: Int)

    private lateinit var data: MutableList<MutableList<Char>>
    private var pos = Position(0, 0)

    override fun preprocess() {
        data = runBlocking<String> { return@runBlocking collect(3) }.split('\n').map { it.map { it }.toMutableList() }.toMutableList()
    }

    private fun getStep(): Boolean {
        val x = data[pos.y]
        return x[pos.x] == '#'
    }

    private fun traverseStep(x: Int, y: Int): Boolean {
        pos.x += x
        pos.x %= 31
        pos.y += y
        return getStep()
    }

    override fun partOne() {
        var steps = 0
        while (true) {
            try {
                if (traverseStep(3, 1))
                    steps += 1
            } catch (e: IndexOutOfBoundsException) {
                println("Total trees: $steps")
                break
            }
        }
    }

    override fun partTwo() {
        val slopes = arrayOf(
                Pair(1, 1),
                Pair(3, 1),
                Pair(5, 1),
                Pair(7, 1),
                Pair(1, 2)
        )
        val num = slopes.map {
            var steps = (0).toBigInteger()
            pos = Position(0, 0)
            while (true) {
                try {
                    if (traverseStep(it.first, it.second))
                        steps += (1).toBigInteger()
                } catch (e: IndexOutOfBoundsException) {
                    break
                }
            }
            steps
        }.reduce { sum, e -> sum * e }
        println("Value=$num")
    }
}