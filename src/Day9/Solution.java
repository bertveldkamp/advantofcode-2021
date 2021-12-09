package Day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solution {
    public static void main(String[] args) throws IOException {
        System.out.println(puzzle1());
        System.out.println(puzzle2());
    }
    public static int puzzle1() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day9/Input.txt"));
        var possibleLowPoints = new ArrayList<PossibleLowPoint>();
        for (int x = 0; x < lines.size(); x++) {
            String[] ys = lines.get(x).split("");
            for (int y = 0; y < ys.length; y++) {
                possibleLowPoints.add(new PossibleLowPoint(
                        Integer.parseInt(ys[y]),
                        x == 0 ? 9 : Integer.parseInt(lines.get(x - 1).split("")[y]),
                        y - 1 <= -1 ? 9 : Integer.parseInt(ys[y-1]),
                        y + 1 >= ys.length ? 9 : Integer.parseInt(ys[y+1]),
                        x + 1 >= lines.size() ? 9 : Integer.parseInt(lines.get(x + 1).split("")[y])
                        ));
            }
        }
        return possibleLowPoints.stream()
                .mapToInt(PossibleLowPoint::calculateRiskLevel)
                .filter(i -> i >= 1)
                .sum();
    }

    public static int puzzle2() throws IOException {
        var lines = Files.readAllLines(Paths.get("./src/Day9/Input.txt"));
        var basinLines = new ArrayList<BasinLine>();
        for (int x = 0; x < lines.size(); x++) {
            var basinBegin = -1;
            String[] ys = lines.get(x).split("");
            var previous = 9;
            for (int y = 0; y < ys.length; y++) {
                if(previous == 9 && previous == Integer.parseInt(ys[y]))
                    continue;
                if(previous == 9)
                    basinBegin = y;
                if(Integer.parseInt(ys[y]) == 9) {
                    basinLines.add(new BasinLine(x, basinBegin, y - 1));
                    basinBegin = -1;
                }
                if(y + 1 == ys.length && basinBegin != -1)
                    basinLines.add(new BasinLine(x, basinBegin, y));
                previous = Integer.parseInt(ys[y]);
            }
        }
        basinLines.forEach(System.out::println);
        var basinList = new ArrayList<>(List.of(new Basin()));
        for (BasinLine basinLine : basinLines) {
            var added = false;
            Basin addedTo = null;
            var remove = new ArrayList<Basin>();
            for (Basin basin : basinList) {
                if(!added) {
                    added = basin.addBasinLine(basinLine);
                    if(added) {
                        addedTo = basin;
                        continue;
                    }
                }
                if(basin.addBasinLine(basinLine)) {
                    if(added) {
                        addedTo.mergeBasin(basin);
                        remove.add(basin);
                    }
                }
            }
            if(added) {
                basinList.removeAll(remove);
                continue;
            }

            var basin = new Basin();
            basin.addBasinLine(basinLine);
            basinList.add(basin);
        }
        basinList.forEach(b -> System.out.println(b.calculateBasinSize()));
        return basinList.stream()
                .mapToInt(b -> -b.calculateBasinSize())
                .sorted()
                .map(i -> -i)
                .limit(3)
                .reduce((i1, i2) -> i1 * i2).getAsInt();
    }


    private record PossibleLowPoint(int height, int above, int left, int right, int below) {
        public int calculateRiskLevel() {
            if(height < above && height < left && height < right && height < below) {
                return height + 1;
            }
            return -1;
        }
    }

    private record BasinLine(int row, int startX, int endX){

    }

    private static class Basin {
        private final List<BasinLine> basinLines = new ArrayList<>();

        public List<BasinLine> getBasinLines() {
            return new ArrayList<>(basinLines);
        }

        public boolean addBasinLine(BasinLine basinLine) {
            if(basinLines.size() == 0) {
                return basinLines.add(basinLine);
            }
            boolean toAdd = basinLines.stream()
                    .filter(bl -> bl.row() == basinLine.row + 1 || bl.row == basinLine.row() - 1)
                    .map(bl -> {
                        var l = new ArrayList<Integer>();
                        for(int i = bl.startX(); i <= bl.endX(); i++)
                            l.add(i);
                        return l;
                    })
                    .anyMatch(al -> {
                        for(int i = basinLine.startX(); i <= basinLine.endX(); i++)
                            if(al.contains(i))
                                return true;
                        return false;
                    });
            if(toAdd) {
                return basinLines.add(basinLine);
            }
            return false;
        }

        public void mergeBasin(Basin other) {
            basinLines.addAll(other.getBasinLines());
        }

        public int calculateBasinSize() {
            return basinLines.stream()
                    .distinct()
                    .mapToInt(bl -> bl.endX() - bl.startX() + 1)
                    .sum();
        }

        @Override
        public String toString() {
            return "Basin{" +
                    "basinLines=" + basinLines +
                    '}';
        }
    }
}