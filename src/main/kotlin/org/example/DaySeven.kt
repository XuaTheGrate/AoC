package org.example

import org.example.aocutil.Day
import kotlinx.coroutines.runBlocking

class DaySeven: Day {
    private val bags = mutableMapOf<String, Array<Pair<Int, String>>>()

    override fun preprocess() {
        runBlocking<String> { return@runBlocking collect(7) }
            .trimIndent()
            .split("\n")
            .forEach {
                val (name, contains) = it.split("contain")
                val theName = transformName(name)
                bags[theName] = transformValue(contains.split(", "))
            }
    }

    private fun transformName(rule: String): String {
        return rule.replace(" bags", "").trim()
    }

    private fun transformValue(rules: List<String>): Array<Pair<Int, String>> {
        return rules.map {
            val split = it.trim().split(" ")
            val num = split.first()
            val name = split.slice(1 until split.size)
                .joinToString(" ")
                .replace(" bags.", "")
                .replace(" bag.", "")
                .replace(" bags", "")
                .replace(" bag", "")
            if (num == "no")
                Pair(0, name)
            else
                Pair(num.toInt(), name)
        }.toTypedArray()
    }

    private fun recursivelyTry(myBag: String, bag: String): Boolean {
        val values = bags[bag] ?: return false
        values.forEach {
            val (_, name) = it
            if (name == myBag)
                return true
            if (recursivelyTry(myBag, name))
                return true
        }
        return false
    }

    override fun partOne() {
        val myBag = "shiny gold"
        var count = 0
        bags.keys.forEach {
            if (recursivelyTry(myBag, it))
                count++
        }
        println("$count total bags can hold $myBag")
    }

    private fun secretTwo(name: String): Int {
        return bags[name]?.map {
            val (count, nname) = it
            count + (count * secretTwo(nname))
        }?.reduce { a, i -> a + i } ?: 0
    }

    override fun partTwo() {
        val total = secretTwo("shiny gold")
        println("$total total bags are required inside a shiny gold")
    }
}