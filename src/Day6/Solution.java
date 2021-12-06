package Day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) throws IOException {
        System.out.println(puzzle1());
        System.out.println(puzzle2());
    }
    public static int puzzle1() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day6/Input.txt"));
        var ages = Arrays.stream(lines.get(0).split(","))
                .map(s -> new AtomicInteger(Integer.parseInt(s.replace(",", ""))))
                .toList();
        List<AtomicInteger> lanternfishes = new ArrayList<>(ages);
        for(int i = 0; i < 80; i++) {
            List<AtomicInteger> newLanternfishes = new ArrayList<>();
            for(AtomicInteger lanternfish : lanternfishes) {
                if(lanternfish.get() == 0) {
                    newLanternfishes.add(new AtomicInteger(8));
                    lanternfish.set(6);
                    continue;
                }
                lanternfish.decrementAndGet();
            }
            lanternfishes.addAll(newLanternfishes);
        }
        return lanternfishes.size();
    }

    public static long puzzle2() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day6/Input.txt"));
        var ages = Arrays.stream(lines.get(0).split(","))
                .map(s -> Integer.parseInt(s.replace(",", "")))
                .collect(Collectors.groupingBy(ai -> ai, Collectors.counting()));
        var lanternfishes = new long[257];
        ages.forEach((key, value) -> lanternfishes[key] = value);
        var lanternfishCount = 300L;
        for (int i = 0; i < lanternfishes.length - 1; i++) {
            var newDay = i + 7;
            var newDayNewFish = newDay + 2;
            lanternfishCount += lanternfishes[i];
            if(newDay >= lanternfishes.length) {
                continue;
            }
            lanternfishes[newDay] = lanternfishes[i] + lanternfishes[newDay];
            if(newDayNewFish >= lanternfishes.length) {
                continue;
            }
            lanternfishes[newDayNewFish] = lanternfishes[i] + lanternfishes[newDayNewFish];
        }
        return lanternfishCount;
    }
}