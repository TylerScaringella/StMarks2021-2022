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

        for(i in 0..this.towers.size) {
            val tower = towers[i]
            if(tower.size == 0) continue

            for(x in 0..towers.size) {
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
    fun solve(depth: Int, prev: MutableSet<CannibalState?>, soln: MutableList<CannibalState?>): List<CannibalState?>? {
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
            val solve: List<CannibalState> = state.solve(depth, prev, soln)
            if (solve != null) {
                soln.add(this)
                return soln
            }
        }
        return null
    }

    /**
     * Determines if there are 3 missionaries & 3 cannibals on the right side
     *
     * @return - The state is the end state
     */
    fun isEnd(): Boolean {
        return leftMissionaries == 0 && leftCannibals == 0
    }

    /**
     * Determines if the missionaries outweigh
     * or are equal to the cannibals on each side to ensure that they do not get eaten
     *
     * @return - The state is legal
     */
    fun isLegal(): Boolean {
        if (!(this.leftCannibals >= 0 && this.leftMissionaries >= 0 && this.rightCannibals >= 0 && this.rightMissionaries >= 0)) return false
        if (this.leftCannibals > this.leftMissionaries &&
                this.leftMissionaries > 0) return false
        return if (this.rightCannibals > this.rightMissionaries &&
                this.rightMissionaries > 0) false else true
    }

    override fun toString(): String? {
        return String.format("%s/%s || %s/%s",
                leftMissionaries,
                leftCannibals,
                rightMissionaries,
                rightCannibals)
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