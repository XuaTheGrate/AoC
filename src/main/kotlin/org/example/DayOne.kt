package org.example

import kotlinx.coroutines.runBlocking
import org.example.aocutil.Day

class DayOne: Day {
    lateinit var nums: Array<Int>

    override fun preprocess() {
        val data = runBlocking<String> { return@runBlocking collect(1) }
        // println(data)
        nums = data.split('\n').map { it.trim('\n', ' ') }.filter { it.isNotEmpty() }.map { it.toInt() }.toTypedArray()
    }

    override fun partOne() {
        for (i in nums) {
            for (j in nums) {
                if (i + j == 2020) {
                    print(i)
                    print(" * ")
                    print(j)
                    print(" = ")
                    println(i * j)
                    return
                }
            }
        }
    }

    override fun partTwo() {
        for (i in nums) {
            for (j in nums) {
                for (k in nums) {
                    if (i + j + k == 2020) {
                        print(i)
                        print(" * ")
                        print(j)
                        print(" * ")
                        print(k)
                        print(" = ")
                        println(i * j * k)
                        return
                    }
                }
            }
        }
    }
}