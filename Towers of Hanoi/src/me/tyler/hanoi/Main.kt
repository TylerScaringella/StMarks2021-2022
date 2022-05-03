package me.tyler.hanoi

import java.util.stream.Collectors

class Main {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val prev = mutableSetOf<RingState>()
            val startState = RingState(3, 3)
            prev.add(startState)

            println(startState.solve(0, prev, mutableListOf())
                    ?.stream()
                    ?.map { it.toString() }
                    ?.collect(Collectors.joining("\n")))
        }
    }
}