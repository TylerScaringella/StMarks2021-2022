package me.tyler.advent;

import me.tyler.advent.util.AdventReader;

import java.io.IOException;
import java.util.*;

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

        System.out.println("Valid: " + passports.stream().filter(Passport::isValid).count());
    }

    private class Passport {
        private Map<String, String> pairs;

        public Passport(String unformatted) {
            this.pairs = new HashMap<>();

            String[] keyValues = unformatted.split("\\s");
            for(String keyValue : keyValues) {
                String[] split = keyValue.split(":");
                this.pairs.put(split[0], split[1]);
            }
        }

        /*
        byr (Birth Year) - four digits; at least 1920 and at most 2002.
        iyr (Issue Year) - four digits; at least 2010 and at most 2020.
        eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
        hgt (Height) - a number followed by either cm or in:
        If cm, the number must be at least 150 and at most 193.
        If in, the number must be at least 59 and at most 76.
        hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
        ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
        pid (Passport ID) - a nine-digit number, including leading zeroes.
        cid (Country ID) - ignored, missing or not.
         */

        private boolean isValid() {
            int birthYear = Integer.parseInt(this.pairs.getOrDefault("byr", "0"));
            int issueYear = Integer.parseInt(this.pairs.getOrDefault("iyr", "0"));
            int expirationYear = Integer.parseInt(this.pairs.getOrDefault("eyr", "0"));
            String height = this.pairs.getOrDefault("hgt", "0");
            String hairColor = this.pairs.getOrDefault("hcl", "0");
            String eyeColor = this.pairs.getOrDefault("ecl", "0");
            int passportId = Integer.parseInt(this.pairs.getOrDefault("pid", "0"));

            if(birthYear < 1920 || birthYear > 2002) return false;
            if(issueYear < 2010 || issueYear > 2020) return false;
            if(expirationYear < 2020 || expirationYear > 2030) return false;
            if(height.contains("cm")) {
                int heightCm = Integer.parseInt(height.replace("cm", ""));
                if(heightCm < 150 || heightCm > 194) return false;
            } else {
                int heightIn = Integer.parseInt(height.replace("in", ""));
                if(heightIn < 59 || heightIn > 76) return false;
            }


            return true;
        }
    }
}
