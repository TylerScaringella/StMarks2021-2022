package me.tyler.cam;

import me.tyler.cam.side.BoatSide;

import java.util.*;

public class CannibalState {
    private final int leftMissionaries, rightMissionaries, leftCannibals, rightCannibals;
    private final BoatSide boatSide;

    public CannibalState(int nmL, int ncL, int nmR, int ncR, BoatSide side) {
        this.leftMissionaries = nmL;
        this.rightMissionaries = nmR;
        this.leftCannibals = ncL;
        this.rightCannibals = ncR;
        this.boatSide = side;
    }

    public CannibalState(int missionaries, int cannibals) {
        this(missionaries, cannibals, 0, 0, BoatSide.LEFT);
    }

    /**
     *
     * @return - Every single possible solution that could occur, coming from this state
     */
    public HashSet<CannibalState> getNextStates() {
        final HashSet<CannibalState> nexts = new HashSet<>();
        for (int nm = 0; nm <= Math.min(2, boatSide == BoatSide.LEFT ?
                this.leftMissionaries : this.rightMissionaries); nm++) {
            for (int nc = (nm==0 ? 1 : 0); nc <= Math.min(2 - nm,
                    boatSide == BoatSide.LEFT ? this.leftCannibals : this.rightCannibals); nc++) {

                CannibalState next;
                if (boatSide == BoatSide.LEFT)
                    next = new CannibalState(this.leftMissionaries -
                            nm, this.leftCannibals - nc, this.rightMissionaries + nm,
                            this.rightCannibals + nc, BoatSide.RIGHT);
                else
                    next = new
                            CannibalState(this.leftMissionaries+nm, this.leftCannibals + nc,
                            this.rightMissionaries - nm, this.rightCannibals - nc, BoatSide.LEFT);
                if (next.isLegal())
                    nexts.add(next);
            }
        }
        return nexts;
    }

    /**
     *
     * @param depth - The current depth in the search
     * @param prev - The current depth
     * @param soln - The legal states
     * @return - Steps
     */
    public List<CannibalState> solve(int depth, Set<CannibalState> prev, List<CannibalState> soln) {
        if(depth > MAX_DEPTH) return null;
        depth+=1;

        if(this.isEnd()) {
            soln.add(this);
            return soln;
        }

        for(CannibalState state : getNextStates()) {
            if(prev.contains(state)) continue;
            prev.add(state);

            final List<CannibalState> solve = state.solve(depth, prev, soln);
            if(solve != null) {
                soln.add(this);
                return soln;
            }
        }

        return null;
    }

    /**
     * Determines if there are 3 missionaries & 3 cannibals on the right side
     *
     * @return - The state is the end state
     */
    public boolean isEnd() {
        return leftMissionaries == 0 && leftCannibals == 0;
    }

    /**
     * Determines if the missionaries outweigh
     * or are equal to the cannibals on each side to ensure that they do not get eaten
     *
     * @return - The state is legal
     */
    public boolean isLegal() {
        if (!(this.leftCannibals >= 0 && this.leftMissionaries >= 0 &&
                this.rightCannibals >= 0 && this.rightMissionaries >= 0)) return false;
        if (this.leftCannibals > this.leftMissionaries &&
                this.leftMissionaries > 0) return false;
        if (this.rightCannibals > this.rightMissionaries &&
                this.rightMissionaries > 0) return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s/%s || %s/%s",
                leftMissionaries,
                leftCannibals,
                rightMissionaries,
                rightCannibals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CannibalState that = (CannibalState) o;
        return leftMissionaries == that.leftMissionaries && rightMissionaries == that.rightMissionaries && leftCannibals == that.leftCannibals && rightCannibals == that.rightCannibals && boatSide == that.boatSide;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftMissionaries, rightMissionaries, leftCannibals, rightCannibals, boatSide);
    }


    private static final int MAX_DEPTH = 1000;
}
