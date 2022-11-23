package twentytwentyone;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day2 {
    ArrayList<String> directions = new ArrayList<>();
    ArrayList<Integer> distances = new ArrayList<>();
    ArrayList<Integer> aimChange = new ArrayList<>();
    ArrayList<Position> positions = new ArrayList<>();
    String fileName = "";

    //Part 2
    public void getAimPoint(DataInputSource dataInputSource) {
        loadDataSource(dataInputSource);
        positions.clear();
        Integer limit = directions.size();

        for(int i=0;i<limit; i++) {
            Position initialPosition;
            if(i > 0) {
                initialPosition = positions.get(i - 1);
            } else {
                initialPosition = new Position(0, 0, 0);
            }

            String direction = directions.get(i);
            Integer distance = distances.get(i);
            Integer upDown = 0;
            Boolean isForward = false;
            if (direction.equals("up") || direction == "up") {
                upDown = -distance;
            }
            if(direction.equals("down") || direction == "down") {
                upDown = distance;
            }
            if(direction.equals("forward") || direction == "forward") {
                isForward = true;
            }
            DepthEvaluator depthEvaluator = new DepthEvaluator(initialPosition.depth,
                    initialPosition.horizontalPosition,
                    initialPosition.aim,
                    upDown,
                    isForward,
                    distance);
            Position outcomePosition = depthEvaluator.getPosition();
            positions.add(i, outcomePosition);
        }
       //positions.stream().forEach(position -> System.out.println("Aim: " + position.aim + " Depth: " + position.depth + " Position: " + position.horizontalPosition));

        Position lastPosition = positions.get(positions.size() - 1);
        System.out.println("Aim: " + lastPosition.aim + " Depth: " + lastPosition.depth + " Position: " + lastPosition.horizontalPosition);
        System.out.println("Multiple : " + lastPosition.depth * lastPosition.horizontalPosition);
    }


    //Part 1
    public void getEndPoint(DataInputSource dataInputSource) {

        loadDataSource(dataInputSource);

        Integer limit = directions.size();

         long forwardTotalDistance = IntStream.range(0, limit)
                 .filter(i -> directions.get(i).contains("forward"))
                .map(i -> distances.get(i)).sum();
        long upwardTotalDistance = IntStream.range(0, limit)
                .filter(i -> directions.get(i).contains("up"))
                .map(i -> distances.get(i)).sum();
        long downwardTotalDistance = IntStream.range(0, limit)
                .filter(i -> directions.get(i).contains("down"))
                .map(i -> distances.get(i)).sum();
        long finalDepth = downwardTotalDistance - upwardTotalDistance;

        long result = finalDepth * forwardTotalDistance;
        System.out.println(result);
    }

    private void loadDataSource(DataInputSource dataInputSource) {
        if (dataInputSource == DataInputSource.SAMPLE) {
            loadSampleValues();
        }
        if (dataInputSource == DataInputSource.TEST) {
            readLines(fileName);
        }
    }

    private void loadSampleValues() {
        directions.clear();
        directions.add("forward");
        directions.add("down");
        directions.add("forward");
        directions.add("up");
        directions.add("down");
        directions.add("forward");

        distances.clear();
        distances.add(5);
        distances.add(5);
        distances.add(8);
        distances.add(3);
        distances.add(8);
        distances.add(2);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void readLines(String fileName) {
        directions.clear();
        distances.clear();
        aimChange.clear();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                directions.add(line.split(Pattern.quote(" "))[0]);
                distances.add(Integer.parseInt(line.split(Pattern.quote(" "))[1].trim()));
                aimChange.add(0);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class DepthEvaluator{
    Integer initialDepth;
    Integer initialHorizonalPosition;
    Integer upDown;
    Boolean isForward;
    Integer movement;
    Integer initialAim;

    public DepthEvaluator(Integer initialDepth, Integer initialHorizonalPosition, Integer initialAim, Integer upDown, Boolean isForward, Integer movement) {
        this.initialDepth = initialDepth;
        this.initialHorizonalPosition = initialHorizonalPosition;
        this.initialAim = initialAim;
        this.upDown = upDown;
        this.isForward = isForward;
        this.movement = movement;
    }

    public Position getPosition() {
        Integer horizonatalPosition = initialHorizonalPosition;
        Integer depth = initialDepth;
        Integer aim = initialAim;
        if(isForward){
            horizonatalPosition += movement;
            depth = initialDepth + initialAim * movement;
        } else {
            aim += upDown;
        }

        return new Position(horizonatalPosition, depth, aim);
    }
}

class Position {
    Integer horizontalPosition;
    Integer depth;
    Integer aim;

    public Position(Integer horizontalPosition, Integer depth, Integer aim) {
        this.horizontalPosition = horizontalPosition;
        this.depth = depth;
        this.aim = aim;
    }
//
//    public Integer getHorizontalPosition() {
//        return horizontalPosition;
//    }
//
//    public void setHorizontalPosition(Integer horizontalPosition) {
//        this.horizontalPosition = horizontalPosition;
//    }
//
//    public Integer getDepth() {
//        return depth;
//    }
//
//    public void setDepth(Integer depth) {
//        this.depth = depth;
//    }
//
//    public Integer getAim() {
//        return aim;
//    }
//
//    public void setAim(Integer aim) {
//        this.aim = aim;
//    }
}
