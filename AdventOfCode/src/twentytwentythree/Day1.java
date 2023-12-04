package twentytwentythree;

import twentytwentyone.AocTest;
import twentytwentyone.DataLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day1 extends DataLoader implements AocTest {
    final static String ONE = "one";
    final static String TWO = "two";
    final static String THREE = "three";
    final static String FOUR = "four";
    final static String FIVE = "five";
    final static String SIX = "six";
    final static String SEVEN = "seven";
    final static String EIGHT = "eight";
    final static String NINE = "nine";


    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());
        return getCalibrationValue(startingList).toString();

    }

    @Override
    public String calculatePartTwo() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        return getCalibrationValue(startingList).toString();
    }

    public Integer getCalibrationValue(List<String> input) {

        List<Integer> calibrationValues = new ArrayList<>();
        // Do it for every row
        for (int i=0; i<input.size(); i++) {
            String line = input.get(i);
            // Get the first digit
            char firstDigit = ' ';
            for (int j=0; j<line.length(); j++){
                char character = line.charAt(j);
                if (character > 47 && character < 58) {
                    firstDigit = character;
                    break;
                }
                // Not a digit
                String charsFromStart = line.substring(0, j + 1);
                Integer intValueIfFound = getIntValueForString(charsFromStart);
                if (intValueIfFound > 48) {
                    firstDigit = (char) intValueIfFound.intValue();
                    break;
                }
            }
            // Get the last digit
            char lastDigit = ' ';
            for (int j=line.length() - 1; j>-1; j--) {
                char character = line.charAt(j);
                if (character > 47 && character < 58) {
                    lastDigit = character;
                    break;
                }
                // Not a digit
                String charsFromEnd = line.substring(j, line.length());
                Integer intValueIfFound = getIntValueForString(charsFromEnd);
                if (intValueIfFound > 48) {
                    lastDigit = (char) intValueIfFound.intValue();
                    break;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(firstDigit);
            stringBuilder.append(lastDigit);
            Integer calibration = Integer.valueOf(stringBuilder.toString());
            System.out.println(calibration);
            calibrationValues.add(calibration);
        }

        return calibrationValues.stream().mapToInt(Integer::intValue).sum();
    }

    public Integer getIntValueForString(String input) {
        Map<Integer, String> numbers = new HashMap<>();
        numbers.put(1, ONE);
        numbers.put(2, TWO);
        numbers.put(3, THREE);
        numbers.put(4, FOUR);
        numbers.put(5, FIVE);
        numbers.put(6, SIX);
        numbers.put(7, SEVEN);
        numbers.put(8, EIGHT);
        numbers.put(9, NINE);

        for (int i = 1; i < 10; i++) {
            String regex = "^.*" + numbers.get(i) + ".*$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                return (i + 48);
            }
        }
        return 0;
    }

}


