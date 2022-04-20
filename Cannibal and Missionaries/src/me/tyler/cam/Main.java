package me.tyler.cam;

import me.tyler.cam.side.BoatSide;

import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        final CannibalState startState = new CannibalState(3,3);
        System.out.println(startState.getNextStates()
            .stream()
            .map(CannibalState::toString)
            .collect(Collectors.joining("\n")));
    }
}
