package org.example

import kotlinx.coroutines.runBlocking
import org.example.aocutil.Day

class DayEight: Day {
    class Op(val pos: Int, var name: String, var num: Int) {

        override fun equals(other: Any?): Boolean {
            return other is Op && other.pos == pos && other.name == name && other.num == num
        }

        override fun toString() = "$pos: $name=$num"

        override fun hashCode(): Int {
            var result = pos
            result = 31 * result + name.hashCode()
            result = 31 * result + num
            return result
        }
    }

    lateinit var data: MutableList<Op>

    override fun preprocess() {
        data = runBlocking<String> { return@runBlocking collect(8) }
            .trimIndent()
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .mapIndexed { idx, it ->
                val (name, num) = it.split(" ")
                Op(idx, name, num.toInt())
            }
            .toMutableList()
    }

    private fun traverse(data: List<Op>): Pair<Boolean, Int> {
        var idx = 0
        var acc = 0
        val usedOps = mutableListOf<Int>()
        while (true) {
            val op = try {
                data[idx]
            } catch (e: IndexOutOfBoundsException) {
                return Pair(true, acc)
            }
            if (idx in usedOps)
                return Pair(false, acc)
            usedOps.add(idx)
            idx += when (op.name) {
                "nop" -> 1
                "acc" -> {
                    acc += op.num
                    1
                }
                "jmp" -> op.num
                else -> throw AssertionError()
            }
        }
    }

    override fun partOne() {
        val (_, acc) = traverse(data)
        println("Duplicated op; Accumulator=$acc")
    }

    override fun partTwo() {
        data.forEachIndexed { _, op ->
            if (op.name == "jmp" || op.name == "nop") {
                val newName = if (op.name == "jmp") "nop" else "jmp"
                val newData = data.slice(0 until data.size).toMutableList()
                newData[op.pos] = Op(op.pos, newName, op.num)
                val (succeed, acc) = traverse(newData)
                if (succeed)
                    println("Terminated. Accumulator value=$acc")
            }
        }
    }
}