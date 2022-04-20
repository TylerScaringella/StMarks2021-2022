package me.tyler.cam;

import me.tyler.cam.side.BoatSide;

import java.util.*;
import java.util.stream.Collectors;

public class CannibalState {
    private final int leftMissionaries, rightMissionaries, leftCannibals, rightCannibals;
    private final BoatSide boatSide;

    public CannibalState(
            int leftMissionaries,
            int rightMissionaries,
            int leftCannibals,
            int rightCannibals,
            BoatSide boatSide) {
        this.leftMissionaries = leftMissionaries;
        this.rightMissionaries = rightMissionaries;
        this.leftCannibals = leftCannibals;
        this.rightCannibals = rightCannibals;
        this.boatSide = boatSide;
    }

    public CannibalState(int missionaries, int cannibals) {
        this(missionaries, 0, cannibals, 0, BoatSide.LEFT);
    }

    /**
     *
     * @return - Every single possible solution that could occur, coming from this state
     */
    public Set<CannibalState> getNextStates() {
        final Set<CannibalState> states = new HashSet<>();
        // I think that we can assume that the maximum we will change the side values by will be 2
        for(int i = 0; i<(boatSide == BoatSide.LEFT ? leftMissionaries : rightMissionaries); i++) {
            for(int x=0; x<(boatSide == BoatSide.LEFT ? leftCannibals : rightCannibals); x++) {
                if(i + x > 2 || i+x == 0) continue;

                final CannibalState state = new CannibalState(
                        i,
                        rightMissionaries+i,
                        x,
                        rightCannibals+x,
                        boatSide == BoatSide.LEFT ? BoatSide.RIGHT : BoatSide.LEFT
                );
                states.add(state);
            }
        }

        return states
                .stream()
                .filter(CannibalState::isLegal)
                .collect(Collectors.toSet());
    }

    /**
     * Determines if there are 3 missionaries & 3 cannibals on the right side
     *
     * @return - The state is the end state
     */
    public boolean isEnd() {
        return rightCannibals == 3
                && rightMissionaries == 0;
    }

    /**
     * Determines if the missionaries outweigh
     * or are equal to the cannibals on each side to ensure that they do not get eaten
     *
     * @return - The state is legal
     */
    public boolean isLegal() {
        // Ensure that there are never less missionaries than there are cannibals
        final boolean missionariesEaten = leftMissionaries >= leftCannibals
                && rightMissionaries >= rightCannibals;

        // Ensure that the total number of missionaries & cannibals never exceed 3 for each of them
        final boolean totalCorrect = (leftMissionaries + rightMissionaries <= MISSIONARY_TOTAL)
                && (leftCannibals + rightCannibals <= CANNIBAL_TOTAL);

        return missionariesEaten && totalCorrect;
    }

    @Override
    public String toString() {
        return String.format("%s/%s || %s/%s",
                leftMissionaries,
                leftCannibals,
                rightMissionaries,
                rightCannibals);
    }

    private static final int MISSIONARY_TOTAL = 3;
    private static final int CANNIBAL_TOTAL = 3;

    private static final int TOTAL = MISSIONARY_TOTAL + CANNIBAL_TOTAL;
}
