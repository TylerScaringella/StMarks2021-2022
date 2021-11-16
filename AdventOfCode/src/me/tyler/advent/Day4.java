package me.tyler.advent;

import me.tyler.advent.util.AdventReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {

    public static void main(String[] args) throws IOException {
        new Day4();
    }

    public Day4() throws IOException {
        final List<String> input = new AdventReader("day4").getLines();
        String inputString = String.join("\n", input);

        String[] passportsDivide = inputString.split("\n\n");
        List<Passport> passports = new ArrayList<>();
        for(String passport : passportsDivide) passports.add(new Passport(passport));

        System.out.println(passports.stream().filter(Passport::isValid).collect(Collectors.toList()).size());
    }

    private class Passport {
        private boolean birthYear,
                issueYear,
                expirationYear,
                height,
                hairColor,
                eyeColor,
                passportID,
                countryID;

        public Passport(String unformatted) {
            if(unformatted.contains("byr")) birthYear = true;
            if(unformatted.contains("iyr")) issueYear = true;
            if(unformatted.contains("eyr")) expirationYear = true;
            if(unformatted.contains("hgt")) height = true;
            if(unformatted.contains("hcl")) hairColor = true;
            if(unformatted.contains("ecl")) eyeColor = true;
            if(unformatted.contains("pid")) passportID = true;
            if(unformatted.contains("cid")) countryID = true;
        }

        public boolean isValid() {
            return birthYear && issueYear && expirationYear && height && hairColor && eyeColor && passportID;
        }
    }
}
