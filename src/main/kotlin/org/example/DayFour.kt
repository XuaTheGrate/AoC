package org.example

import kotlinx.coroutines.runBlocking
import org.example.aocutil.Day

class DayFour: Day {
    private lateinit var passports: List<String>

    private val requiredFields = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    private val eyeColours = arrayOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

    override fun preprocess() {
        passports = runBlocking<String> { return@runBlocking collect(4) }
                .trimIndent()
                .split("\n\n")
    }

    override fun partOne() {
        var valid = 0
        first@for (passport in passports) {
            val fields = passport.split(Regex("\\s"))
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .map { it.split(":").first() }
            for (req in requiredFields) {
                if (req !in fields) {
                    continue@first
                }
            }
            valid++
        }
        println("Detected $valid valid passports")
    }

    override fun partTwo() {
        var valid = 0
        first@for (passport in passports) {
            val reqF = requiredFields.toMutableList()
            val fields = passport.split(Regex("\\s"))
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .map { it.split(":") }
                    .map { Pair(it.first(), it.last()) }
            for ((k, v) in fields) {
                reqF.remove(k)
                when (k) {
                    "byr" -> {
                        val yr = v.toInt()
                        if (yr < 1920 || yr > 2002) {
                            continue@first
                        }
                    }
                    "iyr" -> {
                        val yr = v.toInt()
                        if (yr < 2010 || yr > 2020) {
                            continue@first
                        }
                    }
                    "eyr" -> {
                        val yr = v.toInt()
                        if (yr < 2020 || yr > 2030){
                            continue@first
                        }
                    }
                    "hgt" -> {
                        if (!v.endsWith("cm") && !v.endsWith("in")) {
                            continue@first
                        }
                        val num = v.substring(0..(v.length-3)).toInt()
                        val range = when (val op = v.substring((v.length-2) until v.length)) {
                            "cm" -> 150..193
                            "in" -> 59..76
                            else -> throw AssertionError(op)
                        }
                        if (num !in range) {
                            continue@first
                        }
                    }
                    "hcl" -> {
                        if (!(Regex("#[0-9a-f]{6}").matches(v))) {
                            continue@first
                        }
                    }
                    "ecl" -> {
                        if (v !in eyeColours) {
                            continue@first
                        }
                    }
                    "pid" -> {
                        if (!(Regex("[0-9]{9}").matches(v))) {
                            continue@first
                        }
                    }
                }
            }
            if (reqF.isNotEmpty()) {
                continue@first
            }
            valid++
        }
        println("Detected $valid valid passports")
    }
}