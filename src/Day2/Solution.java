package Day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Solution {
    public static void main(String[] args) throws IOException {
        System.out.println(puzzle1());
        System.out.println(puzzle2());
    }
    public static int puzzle1() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day2/Input.txt"));
        var horizontal = 0;
        var depth = 0;

        for(String line : lines) {
            var space = line.indexOf(" ");
            var movement = line.substring(0, space);
            var amount = Integer.parseInt(line.substring(space).trim());
            switch (movement) {
                case "forward" -> horizontal += amount;
                case "up" -> depth -= amount;
                case "down" -> depth += amount;
                default -> System.out.println("OTHER: " + movement);
            }
        }
        return horizontal * depth;
    }
    public static int puzzle2() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day2/Input.txt"));
        var horizontal = 0;
        var depth = 0;
        var aim = 0;
        for(String line : lines) {
            var space = line.indexOf(" ");
            var movement = line.substring(0, space);
            var amount = Integer.parseInt(line.substring(space).trim());
            switch (movement) {
                case "forward" -> {
                    horizontal += amount;
                    depth += aim * amount;
                }
                case "up" -> aim -= amount;
                case "down" -> aim += amount;
                default -> System.out.println("OTHER: " + movement);
            }
        }
        return horizontal * depth;
    }
}
