package twentytwentytwo;

import twentytwentyone.*;
import util.*;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class Day2 extends DataLoader implements AocTest {
    Map<String, Integer> points;
    Map<String, String> rockPaperScissors;
    Map<String, String> rockPaperScissors2;
    Map<String, String> whatThisBeats;
    Map<String, String> whatBeatsThis;
    Map<String, String> ourValueForRockPaperScissors;
    Map<String, String> theirValueForRockPaperScissors;

    public Day2() {
        intialiseOptionMaps();
    }

    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        Optional<Integer> score = startingList.stream()
                .map(x -> x.split(Pattern.quote(" ")))
                .map(x -> getScoreFromIndividualGame(x))
                .reduce(Integer::sum);

        return score.isPresent() ? score.get().toString() : "0";
    }

    @Override
    public String calculatePartTwo() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        Optional<Integer> score = startingList.stream()
                .map(x -> x.split(Pattern.quote(" ")))
                .map(x -> transformGame(x))
                .map(x -> getScoreFromIndividualGame(x))
                .reduce(Integer::sum);

        return score.isPresent() ? score.get().toString() : "0";
    }

    public String[] transformGame(String[] input) {
        String ourOption = input[1];
        String theirOption = input[0];

        String[] updated = new String[2];
        updated[0] = theirOption;

        String theirOptionExpressed = rockPaperScissors.get(theirOption);
        String ourOptionToWin = whatBeatsThis.get(theirOptionExpressed);
        String ourOptionToLose = whatThisBeats.get(theirOptionExpressed);
        String ourOutputLetter = "";

        if (rockPaperScissors2.get(ourOption) == "DRAW") {
            ourOutputLetter = theirOption;
        }
        if (rockPaperScissors2.get(ourOption) == "WIN") {
            ourOutputLetter = ourValueForRockPaperScissors.get(ourOptionToWin);
        }
        if (rockPaperScissors2.get(ourOption) == "LOSE") {
            ourOutputLetter = ourValueForRockPaperScissors.get(ourOptionToLose);
        }
        updated[1] = ourOutputLetter;
        return updated;
    }
    public Integer getScoreFromIndividualGame(String[] input) {
        Integer finalScore = 0;
        String ourOption = input[1];
        String theirOption = input[0];
        Integer ourOptionValue = points.get(ourOption);

        finalScore += ourOptionValue;

        String ours = rockPaperScissors.get(ourOption);
        String theirs = rockPaperScissors.get(theirOption);

        if (ours.equals(theirs)) {
            finalScore += 3;
        }

        if (whatThisBeats.get(ours).equals(theirs)) {
            finalScore += 6;
        }

        return finalScore;
    }

    private void intialiseOptionMaps(){
        points = new HashMap<>();
        points.put("A", 1);
        points.put("B", 2);
        points.put("C", 3);
        points.put("X", 1);
        points.put("Y", 2);
        points.put("Z", 3);

        rockPaperScissors = new HashMap<>();
        rockPaperScissors.put("A", "ROCK");
        rockPaperScissors.put("B", "PAPER");
        rockPaperScissors.put("C", "SCISSORS");
        rockPaperScissors.put("X", "ROCK");
        rockPaperScissors.put("Y", "PAPER");
        rockPaperScissors.put("Z", "SCISSORS");

        rockPaperScissors2 = new HashMap<>();
        rockPaperScissors2.put("A", "ROCK");
        rockPaperScissors2.put("B", "PAPER");
        rockPaperScissors2.put("C", "SCISSORS");
        rockPaperScissors2.put("X", "LOSE");
        rockPaperScissors2.put("Y", "DRAW");
        rockPaperScissors2.put("Z", "WIN");

        whatThisBeats = new HashMap<>();
        whatThisBeats.put("ROCK", "SCISSORS");
        whatThisBeats.put("PAPER", "ROCK");
        whatThisBeats.put("SCISSORS", "PAPER");

        whatBeatsThis = new HashMap<>();
        whatBeatsThis.put("ROCK", "PAPER");
        whatBeatsThis.put("PAPER", "SCISSORS");
        whatBeatsThis.put("SCISSORS", "ROCK");

        ourValueForRockPaperScissors = new HashMap<>();
        ourValueForRockPaperScissors.put("ROCK", "X");
        ourValueForRockPaperScissors.put("PAPER", "Y");
        ourValueForRockPaperScissors.put("SCISSORS", "Z");

        theirValueForRockPaperScissors = new HashMap<>();
        theirValueForRockPaperScissors.put("ROCK", "A");
        theirValueForRockPaperScissors.put("PAPER", "B");
        theirValueForRockPaperScissors.put("SCISSORS", "C");
    }

}
