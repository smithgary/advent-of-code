package twentytwentyone;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 extends DataLoader {

    List<Line> lines = new ArrayList<>();
    Integer[][] grid;
    Integer maxX;
    Integer maxY;

    public void calculateIntersectionsHorizontalVertical(DataInputSource dataInputSource){
        loadLines(dataInputSource);

        lines.stream().filter(line -> line.determinePointsOnLine().size() > 0).forEach(line -> {
            line.determinePointsOnLine().stream().forEach(point -> grid[point.x][point.y] +=1);
        });
        sumUp();
    }
    public void calculateIntersectionsHorizontalVerticalAndDiagonal(DataInputSource dataInputSource){
        loadLines(dataInputSource);

        lines.stream().filter(line -> line.getAllPointsOnLineIncludingDiagonal().size() > 0).forEach(line -> {
            line.getAllPointsOnLineIncludingDiagonal().stream().forEach(point -> grid[point.x][point.y] +=1);
        });
        sumUp();
    }

    private void sumUp(){
        Integer countOver1 = 0;
        for (int i = 0; i < maxX+1; i++) {
            for (int j = 0; j < maxY+1; j++) {
                if(grid[i][j] > 1) {
                    countOver1 +=1;
                }
            }
        }
        System.out.println("Count :" + countOver1);
    }

    public void loadLines(DataInputSource dataInputSource) {
        lines.clear();
        String regex = "(\\d+),(\\d+)\\s\\-\\>\\s(\\d+),(\\d+)";
        loadDataSource(dataInputSource, 1);
        final List<List<Integer>> collect = column1.stream()
                .map(String::trim)
                .map(line -> integersFromRegexGroups(line, regex, 4))
                .collect(Collectors.toList());

        collect.stream().forEach(integerList -> {
            Point first = new Point(integerList.get(0), integerList.get(1));
            Point second = new Point(integerList.get(2), integerList.get(3));
            lines.add(new Line(first, second));
        });

        Optional<Integer> maxXMaybe = lines.stream().map(line -> line.getHighestX()).reduce(Integer::max);
        Optional<Integer> maxYMaybe = lines.stream().map(line -> line.getHighestY()).reduce(Integer::max);
        maxX = maxXMaybe.get();
        maxY = maxYMaybe.get();
        grid = new Integer[maxX+1][maxY+1];
        for (int i = 0; i < maxX+1; i++) {
            for (int j = 0; j < maxY+1; j++) {
                grid[i][j] = 0;
            }
        }
    }

    private List<Integer> integersFromRegexGroups(String input, String regex, Integer groupCount) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String[] strings = new String[groupCount];
        if(matcher.find()) {
            //Not clear on why this has to be +1, ie first match group looks like the whole thing.
            IntStream.range(0, groupCount).forEach(i -> strings[i] = matcher.group(i+1));
        }
        return Arrays.stream(strings).map(Integer::valueOf).collect(Collectors.toList());
    }

    @Override
    public void loadSampleValues() {
        column1.clear();
        column1.add("0,9 -> 5,9");
        column1.add("8,0 -> 0,8");
        column1.add("9,4 -> 3,4");
        column1.add("2,2 -> 2,1");
        column1.add("7,0 -> 7,4");
        column1.add("6,4 -> 2,0");
        column1.add("0,9 -> 2,9");
        column1.add("3,4 -> 1,4");
        column1.add("0,0 -> 8,8");
        column1.add("5,5 -> 8,2");
    }
}

class Line {
    List<Point> pointsOnLine = new ArrayList<>();
    public Point start;
    public Point end;
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
    public Integer getHighestX(){
        return (this.start.x > this.end.x) ? this.start.x : this.end.x;
    }
    public Integer getHighestY() {
        return (this.start.y > this.end.y) ? this.start.y : this.end.y;
    }
    public List<Point> getAllPointsOnLineIncludingDiagonal(){
        pointsOnLine.clear();
        List<Point> pointsFromVerticalAndHorizonalLines = determinePointsOnLine();
        List<Point> pointsFromDiagonalLines = determineDiagonalPointsOnLine();
        return  (pointsFromVerticalAndHorizonalLines.size() > pointsFromDiagonalLines.size())
                ? pointsFromVerticalAndHorizonalLines
                : pointsFromDiagonalLines;
    }

    public List<Point> determineDiagonalPointsOnLine() {
        //if delta x = delta y , diagonal
        if(Math.abs(start.x - end.x) == Math.abs(start.y - end.y)) {
            if(start.y > end.y) {
                if(start.x > end.x) {
                    //       / start
                    //     /
                    //   /end
                    IntStream.rangeClosed(end.y, start.y).forEach(y -> {
                        Integer iteration = y - end.y;
                        pointsOnLine.add(new Point(end.x + iteration, y));
                    });
                } else {
                    //   \ start
                    //     \
                    //      \end
                    IntStream.rangeClosed(end.y, start.y).forEach(y -> {
                        Integer iteration = y - end.y;
                        pointsOnLine.add(new Point(end.x - iteration, y));
                    });
                }
            } else { //end.y > start.y
                if(start.x > end.x) {
                    //   \ end
                    //     \
                    //      \start
                    IntStream.rangeClosed(start.y, end.y).forEach(y -> {
                        Integer iteration = y - start.y;
                        pointsOnLine.add(new Point(start.x - iteration, y));
                    });
                } else {
                    //       / end
                    //     /
                    //   /start
                    IntStream.rangeClosed(start.y, end.y).forEach(y -> {
                        Integer iteration = y - start.y;
                        pointsOnLine.add(new Point(start.x + iteration, y));
                    });
                }
            }
        }
        return pointsOnLine;
    }
    public List<Point> determinePointsOnLine () {
        //Only calculate vertical and horizontal for now.
        pointsOnLine.clear();
        if(start.x.equals(end.x)) {
            //range must go from smallest to largest
            if(start.y > end.y) {
                IntStream.rangeClosed(end.y, start.y).forEach(y -> {
                    pointsOnLine.add(new Point(start.x, y));
                });
            } else {
                IntStream.rangeClosed(start.y, end.y).forEach(y -> {
                    pointsOnLine.add(new Point(start.x, y));
                });
            }
        }
        if(start.y.equals(end.y)) {
            if(start.x > end.x) {
                IntStream.rangeClosed(end.x, start.x).forEach(x -> {
                    pointsOnLine.add(new Point(x, start.y));
                });
            } else {
                IntStream.rangeClosed(start.x, end.x).forEach(x -> {
                    pointsOnLine.add(new Point(x, start.y));
                });
            }
        }
        return pointsOnLine;
    }
}

class Point {
    public Integer x;
    public Integer y;
    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}
