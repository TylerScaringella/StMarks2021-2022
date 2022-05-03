package me.tyler.hanoi

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class RingState {
    private val towers: ArrayList<ArrayList<Int>>

    constructor(numRings: Int, numTowers: Int) {
        this.towers = ArrayList()
        for(i in 0..numTowers) {
            towers.add(ArrayList())
        }
        for(i in 0..numRings) {
            towers[0].add(i)
        }
    }

    constructor(towers: ArrayList<ArrayList<Int>>) {
        this.towers = towers
    }

    fun copy(): ArrayList<ArrayList<Int>> {
        val list = ArrayList<ArrayList<Int>>()

        for(tower in towers) {
            val towerList = ArrayList<Int>()

            for(i in tower) {
                towerList.add(i)
            }

            list.add(towerList)
        }

        return list
    }

    /**
     * @return - Every single possible solution that could occur, coming from this state
     */
    fun getNextStates(): HashSet<RingState> {
        val states = HashSet<RingState>()

        for(i in 0 until (this.towers.size-1)) {
            val tower = towers[i]
            if(tower.size == 0) continue

            for(x in 0 until (towers.size-1)) {
                val towers = this.copy()
                val value = towers[i].removeAt(0)
                towers[x].add(value)

                val next = RingState(towers)
                if(next.isLegal())
                    states.add(next)
            }
        }

        return states
    }

    /**
     *
     * @param depth - The current depth in the search
     * @param prev - The current depth
     * @param soln - The legal states
     * @return - Steps
     */
    fun solve(depth: Int, prev: MutableSet<RingState>, soln: MutableList<RingState>): List<RingState>? {
        var depth = depth
        if (depth > MAX_DEPTH) return null
        depth += 1
        if (isEnd()) {
            soln.add(this)
            return soln
        }
        for (state in getNextStates()) {
            if (prev.contains(state)) continue
            prev.add(state)
            val solve: List<RingState>? = state.solve(depth, prev, soln)
            if (solve != null) {
                soln.add(this)
                return soln
            }
        }
        return null
    }

    /**
     * Determines if every tower except for the last tower is empty
     *
     * @return - The state is the end state
     */
    fun isEnd(): Boolean {
        for(i in 0 until (towers.size-1)) {
            val tower = towers[i]
            if(tower.isNotEmpty()) return false
        }

        return true
    }

    /**
     * Ensures that there are no higher integers above lower integers in the same tower
     *
     * @return - The state is legal
     */
    fun isLegal(): Boolean {
        var legal = true

        for(tower in towers) {
            var prevVal = 0
            // the previous value should always be less
            for(value in tower) {
                if(value > prevVal) return false
                prevVal = value
            }
        }

        return legal
    }

    override fun toString(): String {
        var string = ""

        for(tower in towers) {
            println(tower.joinToString(", "))
        }

        return string
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RingState

        if (towers != other.towers) return false

        return true
    }

    override fun hashCode(): Int {
        return towers.hashCode()
    }

    companion object {
        private val MAX_DEPTH = 1000
    }

}