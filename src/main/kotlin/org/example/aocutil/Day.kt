package org.example.aocutil

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.File
import java.nio.charset.Charset

interface Day {
    fun getHttpEngine(): HttpClient {
        return HttpClient(CIO)
    }

    suspend fun collect(day: Int): String {
        val file = File("day-$day.txt")
        if (file.exists())
            return file.readText(Charset.forName("UTF-8"))
        val resp = getHttpEngine().get<HttpResponse>("https://adventofcode.com/2020/day/$day/input") {
            header("Cookie", "session=${System.getenv("AOC_COOKIE")}")
        }
        if (resp.status.value != 200) {
            throw RuntimeException("Invalid response code: ${resp.status.value} ${resp.status.description}")
        }
        val read = resp.readText(Charset.forName("UTF-8"))
        file.writeText(read, Charset.forName("UTF-8"))
        return read
    }

    fun preprocess()
    fun main() {
        preprocess()
        partOne()
        partTwo()
    }
    fun partOne()
    fun partTwo()
}