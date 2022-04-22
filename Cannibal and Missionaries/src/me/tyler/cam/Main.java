package me.tyler.cam;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        final Set<CannibalState> prev = new HashSet<>();
        final CannibalState startState = new CannibalState(3,3);
        prev.add(startState);

        System.out.println(startState.getNextStates()
            .stream()
            .map(CannibalState::toString)
            .collect(Collectors.joining("\n")));

        startState.solve(0, prev);
    }
}
