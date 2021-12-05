package Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Solution {
    public static void main(String[] args) throws IOException {
        System.out.println(puzzle1());
        System.out.println(puzzle2());
    }
    public static int puzzle1() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day3/Input.txt"));
        Map<Integer, AtomicInteger> bitMap = new HashMap<>();
        for (int i = 0; i < lines.get(0).length(); i++) {
            bitMap.computeIfAbsent(i, (in) -> new AtomicInteger());
        }
        for (String line : lines) {
            var chars = new StringBuilder(line).reverse().toString().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if(chars[i] == '1') {
                    bitMap.get(i).getAndIncrement();
                }
            }
        }
        var gamma = 0;
        var epsilon = 0;

        for (int i = 0; i < bitMap.size(); i++) {
            if(bitMap.get(i).get() > lines.size() / 2) {
                gamma = (int) (gamma + Math.pow(2, i));
                continue;
            }
            epsilon = (int) (epsilon + Math.pow(2, i));
        }

        return gamma * epsilon;
    }
    public static int puzzle2() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day3/Input.txt"));
        var oxygen = new ArrayList<>(lines);
        var CO2 = new ArrayList<>(lines);
        var index = 0;
        while (oxygen.size() > 1) {
            var count = 0;
            for(String line : oxygen) {
                if(line.toCharArray()[index] == '1') count++;
            }
            final int index2 = index, count2 = count;
            oxygen.removeIf(s -> s.charAt(index2) == (count2 >= Math.round(oxygen.size() / 2.0) ? '0' : '1'));
            index++;
        }
        System.out.println(oxygen);
        index = 0;
        while (CO2.size() > 1) {
            var count = 0;
            for(String line : CO2) {
                if(line.toCharArray()[index] == '1') count++;
            }
            final int index2 = index, count2 = count;
            CO2.removeIf(s -> s.charAt(index2) == (count2 >= Math.round(CO2.size() / 2.0) ? '1' : '0'));
            index++;
        }
        System.out.println(CO2);
        return Integer.parseInt(oxygen.get(0), 2) * Integer.parseInt(CO2.get(0), 2);
    }
}
