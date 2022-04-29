package me.tyler.hanoi

import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

class Main {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val prev = mutableSetOf<RingState>()
            val startState = RingState(3)
            prev.add(startState)
//            println(
//                    startState.solve(0, prev, ArrayList<CannibalState>())
//                            .stream()
//                            .map<String>(Function<CannibalState, String> { obj: CannibalState -> obj.toString() })
//                            .collect(Collectors.joining("\n")))
        }
    }
}