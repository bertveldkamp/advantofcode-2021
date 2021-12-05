package Day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) throws IOException {
        System.out.println(puzzle2());
    }
    public static int puzzle1() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day5/Input.txt"));
        var count = 0;
        var map = new int[1000][1000];

        for (String line : lines) {
            var coordinates = line.replace(" -> ", " ").split(" ");
            var x1 = Integer.parseInt(coordinates[0].split(",")[0].trim());
            var y1 = Integer.parseInt(coordinates[0].split(",")[1].replace(",",  "").trim());
            var x2 = Integer.parseInt(coordinates[1].split(",")[0].trim());
            var y2 = Integer.parseInt(coordinates[1].split(",")[1].replace(",",  "").trim());
            if(x1 == x2)
                drawHorizontal(x1, y1, y2, map);
            else if(y1 == y2)
                drawVertical(x1, x2, y1, map);
        }
        for (int[] ints : map) {
            for (int anInt : ints) {
                if(anInt >= 2)
                    count++;
            }
        }

        return count;
    }

    private static void drawHorizontal(int x, int y1, int y2, int[][] map) {
        while (y1 != y2) {
            map[x][y1]++;
            y1 = y1 < y2 ? y1 + 1 : y1 - 1;
        }
        map[x][y1]++;
    }

    private static void drawVertical(int x1, int x2, int y, int[][] map) {
        while (x1 != x2) {
            map[x1][y]++;
            x1 = x1 < x2 ? x1 + 1 : x1 - 1;
        }
        map[x1][y]++;
    }

    private static void drawDiagonal(int x1, int y1, int x2, int y2, int[][] map) {
        while (x1 != x2) {
            map[x1][y1]++;
            x1 = x1 < x2 ? x1 + 1 : x1 - 1;
            y1 = y1 < y2 ? y1 + 1 : y1 - 1;
        }
        map[x1][y1]++;
    }

    public static int puzzle2() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day5/Input.txt"));
        var count = 0;
        var map = new int[1000][1000];

        for (String line : lines) {
            var coordinates = line.replace(" -> ", " ").split(" ");
            var x1 = Integer.parseInt(coordinates[0].split(",")[0].trim());
            var y1 = Integer.parseInt(coordinates[0].split(",")[1].replace(",",  "").trim());
            var x2 = Integer.parseInt(coordinates[1].split(",")[0].trim());
            var y2 = Integer.parseInt(coordinates[1].split(",")[1].replace(",",  "").trim());
            if(x1 == x2)
                drawHorizontal(x1, y1, y2, map);
            else if(y1 == y2)
                drawVertical(x1, x2, y1, map);
            else
                drawDiagonal(x1, y1, x2, y2, map);
        }
        for (int[] ints : map) {
            for (int anInt : ints) {
                if(anInt >= 2)
                    count++;
            }
        }

        return count;
    }
}