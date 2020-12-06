package org.example

import kotlinx.coroutines.runBlocking
import org.example.aocutil.Day

class DaySix: Day {
    private lateinit var data: Array<String>

    override fun preprocess() {
        data = runBlocking<String> { return@runBlocking collect(6) }
                .split("\n\n")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .toTypedArray()
    }

    override fun partOne() {
        val nums = data.map {
            val set = it.toList().filter { it != '\n' }.distinct()
            set.size
        }
        println("Sum = ${nums.reduce { a, v -> a + v }}")
    }

    override fun partTwo() {
        val nums = data.map { group ->
            val groups = group.split("\n")
            var main = groups[0].toSet()
            if (groups.size == 1)
                main.size
            else {
                groups.forEach {
                    main = main.intersect(it.toSet())
                }
                main.size
            }
        }
        println("Sum = ${nums.reduce { a, v -> a + v }}")
    }
}