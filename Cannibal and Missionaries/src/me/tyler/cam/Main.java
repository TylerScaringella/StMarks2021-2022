package me.tyler.cam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        final Set<CannibalState> prev = new HashSet<>();
        final CannibalState startState = new CannibalState(5,4);
        prev.add(startState);

        System.out.println(
                startState.solve(0, prev, new ArrayList<>())
                        .stream()
                        .map(CannibalState::toString)
                        .collect(Collectors.joining("\n")));
    }
}
