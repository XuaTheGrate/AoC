package org.example.aocutil

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.nio.charset.Charset

interface Day {
    fun getHttpEngine(): HttpClient {
        return HttpClient(CIO)
    }

    suspend fun collect(day: Int): String {
        val resp = getHttpEngine().get<HttpResponse>("https://adventofcode.com/2020/day/$day/input") {
            header("Cookie", "session=${System.getenv("AOC_COOKIE")}")
        }
        if (resp.status.value != 200) {
            throw RuntimeException("Invalid response code: ${resp.status.value} ${resp.status.description}")
        }
        return resp.readText(Charset.forName("UTF-8"))
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