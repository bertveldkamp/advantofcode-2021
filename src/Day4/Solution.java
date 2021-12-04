package Day4;

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
        var lines = Files.readAllLines(Paths.get("./src/Day4/Input.txt"));
        var numbers = Arrays.stream(lines.get(0).split(","))
                .map(s -> s.replace(',', ' '))
                .map(Integer::valueOf)
                .toList();
        var sum = 0;
        for (int i = 4; i < numbers.size(); i++) {
            for (int i2 = 2; i2 < lines.size(); i2 += 6) {
                var board = createBoard(lines.subList(i2, i2+5));
                if(checkBingo(board, numbers.subList(0, i  + 1))){
                    var called = numbers.subList(0, i + 1);
                    return board.keySet().stream()
                            .filter(integer -> !called.contains(integer))
                            .mapToInt(Integer::intValue)
                            .sum() * called.get(called.size() - 1);
                }
            }
        }
        return sum;
    }

    private static boolean checkBingo(Map<Integer, Integer> board, List<Integer> numbers) {
        var indexes = numbers.stream()
                .filter(board::containsKey)
                .map(board::get)
                .collect(Collectors.toList());
        if (indexes.size() < 5) return false;
        indexes.sort(Comparator.naturalOrder());
        return checkHorizontal(indexes) || checkVertical(indexes);
    }

    private static Map<Integer, Integer> createBoard (List<String> board) {
        Map<Integer, Integer> boardMap = new HashMap<>();
        for (int i = 0; i < board.size(); i++) {
            var count = 0;
            for (String s1 : board.get(i).split(" ")) {
                if((s1 = s1.strip()).equals("")) continue;
                boardMap.put(Integer.parseInt(s1), i*5+count++);
            }
        }
        return boardMap;
    }

    private static boolean checkHorizontal(List<Integer> indexes) {
        var chain = 0;
        var last = Integer.MIN_VALUE;
        for (int index : indexes) {
            if((index % 5 == 0))
                chain = 0;
            chain = index == last + 1 ? chain + 1 : 1;
            last = index;
            if(chain == 5)
                return true;
        }
        return false;
    }

    private static boolean checkVertical(List<Integer> indexes) {
        var chain = 1;
        for (int index : indexes) {
            if (index > 4) {
                continue;
            }
            var checkIndex = index;
            while(indexes.contains(checkIndex + 5)) {
                chain++;
                if(chain == 5)
                    return true;
                checkIndex += 5;
            }
            chain = 1;
        }
        return false;
    }

    public static int puzzle2() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day4/Input.txt"));
        var boards = lines.subList(2, lines.size());
        var numbers = Arrays.stream(lines.get(0).split(","))
                .map(s -> s.replace(',', ' '))
                .map(Integer::valueOf)
                .toList();
        var sum = 0;
        for (int i = 4; i < numbers.size(); i++) {
            List<Integer> toRemoveList = new ArrayList<>();
            for (int i2 = 0; i2 < boards.size(); i2 += 6) {
                var board = createBoard(boards.subList(i2, i2 + 5));
                if (checkBingo(board, numbers.subList(0, i + 1))) {
                    toRemoveList.add(i2);
                }
            }
            toRemoveList.sort(Comparator.reverseOrder());
            for (int index : toRemoveList) {
                var board = createBoard(boards.subList(index, index + 5));
                var called = new ArrayList<>(numbers.subList(0, i + 1));

                if(boards.size() == 5 || boards.size() == 6) {
                    return board.keySet().stream()
                            .filter(integer -> !called.contains(integer))
                            .mapToInt(Integer::intValue)
                            .sum() * called.get(called.size() - 1);
                }

                boards.remove(index);
                boards.remove(index);
                boards.remove(index);
                boards.remove(index);
                boards.remove(index);
                if (!(index == boards.size()))
                    boards.remove(index);
            }

        }
        return sum;
    }
}