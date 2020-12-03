package org.example

import kotlinx.coroutines.runBlocking
import org.example.aocutil.Day

class DayTwo: Day {
    private lateinit var passwords: Array<Triple<IntRange, Char, String>>
    private val re = Regex("^([0-9]{1,2})-([0-9]{1,2}) ([a-z]): ([a-zA-Z]+)$")

    override fun preprocess() {
        passwords = runBlocking<String> { return@runBlocking collect(2) }
                .split("\n")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map {
                    val groups = re.matchEntire(it)?.groups ?: throw AssertionError()
                    Triple(
                            groups[1]!!.value.toInt()..groups[2]!!.value.toInt(),
                            groups[3]!!.value[0],
                            groups[4]!!.value
                    )
                }
                .toTypedArray()
    }

    override fun partOne() {
        var valid = 0

        passwords.forEach {
            val (range, char, pw) = it
            if (pw.count { it == char } in range)
                valid++
        }
        println("$valid valid passwords detected")
    }

    override fun partTwo() {
        var valid = 0

        passwords.forEach {
            val (range, char, pw) = it
            val min = range.first - 1
            val max = range.last - 1
            if (pw[min] == char) {
                if (pw[max] != char)
                    valid++
            } else if (pw[max] == char) {
                if (pw[min] != char)
                    valid++
            }
        }
        println("$valid valid passwords detected")
    }
}