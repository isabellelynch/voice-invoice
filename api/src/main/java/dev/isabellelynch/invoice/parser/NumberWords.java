package dev.isabellelynch.invoice.parser;

import java.util.List;
import java.util.Optional;
import java.util.Map;

public final class NumberWords {

    private NumberWords() {
    }
    private static final Map<String, Integer> SMALL = Map.ofEntries(
        Map.entry("zero", 0), 
        Map.entry("one", 1), 
        Map.entry("two", 2),
        Map.entry("three", 3),
        Map.entry("four", 4),
        Map.entry("five", 5),
        Map.entry("six", 6),
        Map.entry("seven", 7),
        Map.entry("eight", 8),
        Map.entry("nine", 9),
        Map.entry("ten", 10),
        Map.entry("eleven", 11),
        Map.entry("twelve", 12),
        Map.entry("thirteen", 13),
        Map.entry("fourteen", 14),
        Map.entry("fifteen", 15),
        Map.entry("sixteen", 16),
        Map.entry("seventeen", 17),
        Map.entry("eighteen", 18),
        Map.entry("nineteen", 19),
        Map.entry("twenty", 20), 
        Map.entry("thirty", 30),
        Map.entry("forty", 40),
        Map.entry("fifty", 50),
        Map.entry("sixty", 60),
        Map.entry("seventy", 70),
        Map.entry("eighty", 80),
        Map.entry("ninety", 90)
    );

    public static Optional<Long> parseWhole(List<String> words) {
        long total = 0;
        long current = 0;
        int lastSmall = -1;
        boolean sawNumber = false;
        for(int i = 0; i < words.size(); i++){
            String word = words.get(i);
            Integer small = SMALL.get(word);

            if(small != null){
                if (lastSmall != -1) {
                    boolean previousIsTens = lastSmall >= 20 && lastSmall % 10 == 0;
                    boolean thisIsUnits = small >= 1 && small <= 9;
                    if (!previousIsTens || !thisIsUnits) {
                        return Optional.empty();
                    }
                }
                current += small;
                lastSmall = small;
                sawNumber = true;
            } else if (word.equals("hundred")) {
                if (current == 0) {
                    current = 1;
                }
                current *= 100;
                lastSmall = -1;
                sawNumber = true;
            } else if (word.equals("thousand")) {
                if (current == 0) {
                    current = 1;
                }
                total += current * 1000;
                current = 0;
                lastSmall = -1;
                sawNumber = true;
            } else if (word.equals("and")) {
                continue;
            } else if(word.equals("a")){
                if (i + 1 < words.size() && (words.get(i + 1).equals("hundred") || words.get(i + 1).equals("thousand"))) {
                    continue;
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.empty();
            }
        }

        if(!sawNumber) {
            return Optional.empty();
        }

        return Optional.of(total + current);
    }
}
