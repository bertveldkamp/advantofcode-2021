package Day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) throws IOException {
        System.out.println(puzzle1());
        System.out.println(puzzle2());
    }
    public static int puzzle1() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day1/Input.txt"));
        var count = 0;
        for(int i = 0, j = 1; j < lines.size(); i++, j++){
            if(Integer.parseInt(lines.get(i))< Integer.parseInt(lines.get(j))) count++;
        }
        return count;
    }
    public static int puzzle2() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day1/Input.txt")).stream().map(Integer::parseInt).toList();
        var count = 0;
        for(int i = 0, j = 1, k = 2, l = 3; l < lines.size(); i++, j++, k++, l++){
            var middle = lines.get(j) + lines.get(k);
            var window1 = lines.get(i) + middle;
            var window2 = middle + lines.get(l);
            if(window1 < window2) {
                count++;
            }
        }
        return count;
    }
}
