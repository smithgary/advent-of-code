package twentytwentyone;

import java.util.stream.IntStream;

public class AdventOfCode21 {
    public static void main(String[] args) {
        Integer dayToRun = 10;
        Integer partOneOrTwo = 1;
        //Integer inputSource = DataInputSource.SAMPLE.getValue();
        Integer inputSource = DataInputSource.TEST.getValue();


        /**
         * Day 1
         */
        if (dayToRun == 1) {
            Day1 day1 = new Day1();
            String day1Input = "/Users/gvsmith/Projects/Blackjack/AdventOfCode21/src/data/day1sample";
            day1.setFileName(day1Input);
            //Run
            day1.countDecreasing(DataInputSource.SAMPLE.getValue());    //1466
            day1.countThree(DataInputSource.SAMPLE.getValue());         //1491

            day1.countDecreasing(DataInputSource.TEST.getValue());      //1466
            day1.countThree(DataInputSource.TEST.getValue());           //1491
        }

        /**
         * Day 2
         */
        if (dayToRun == 2) {
            Day2 day2 = new Day2();
            day2.setFileName("/Users/gvsmith/Projects/Blackjack/AdventOfCode21/src/data/day2sample");
            //Part 1
            day2.getEndPoint(DataInputSource.SAMPLE);       //150
            day2.getEndPoint(DataInputSource.TEST);         //1635930

            //Part 2
            day2.getAimPoint(DataInputSource.SAMPLE);       //900
            day2.getAimPoint(DataInputSource.TEST);         //1781819478
        }

        /**
         * Day 3
         * Had to write a recursive function to achieve part 2.
         */
        if (dayToRun == 3) {
            Day3 day3 = new Day3();
            day3.setFileName("/Users/gvsmith/Projects/Blackjack/AdventOfCode21/src/data/day3sample");
            day3.getGammaEpsilon(DataInputSource.SAMPLE);   //198
            day3.getGammaEpsilon(DataInputSource.TEST);     //1025636

            day3.getOxygenCO2(DataInputSource.SAMPLE);      //230
            day3.getOxygenCO2(DataInputSource.TEST);        //793873

        }
        /**
         * Day 4
         */
        if (dayToRun == 4) {
            Day4 day4 = new Day4();
            day4.setFileName("/Users/gvsmith/Projects/Blackjack/AdventOfCode21/src/data/day4sample");

            day4.getBingo(DataInputSource.SAMPLE);          // 24 * 188 = 4512
            day4.getBingo(DataInputSource.TEST);                // 41 * 870 = 35670

            // part2
            day4.getLastBingoWinningCard(DataInputSource.SAMPLE);   // 13 * 148 = 1924
            day4.getLastBingoWinningCard(DataInputSource.TEST);   // 88 * 258 = 22704
        }
        /**
         * Day 5
         * Learned to use IntStream.rangeClosed
         * as opposed to IntStream.range, which doesn't include the last item.
         */
        if (dayToRun == 5) {
            Day5 day5 = new Day5();
            day5.setFileName("/Users/gvsmith/Projects/Blackjack/AdventOfCode21/src/data/day5sample");

            day5.calculateIntersectionsHorizontalVertical(DataInputSource.SAMPLE);  // 5
            day5.calculateIntersectionsHorizontalVertical(DataInputSource.TEST);    //6461

            //part2
            day5.calculateIntersectionsHorizontalVerticalAndDiagonal(DataInputSource.SAMPLE); //12
            day5.calculateIntersectionsHorizontalVerticalAndDiagonal(DataInputSource.TEST);     //18065
        }
        /**
         * Day 6
         */
        if (dayToRun == 6) {
            Day6 day6 = new Day6();
            day6.setFileName("/Users/gvsmith/Projects/Blackjack/AdventOfCode21/src/data/day6sample");
            // Part 1
            //day6.countLanternFish(DataInputSource.SAMPLE, 80);  //80 -> 5934
            //day6.countLanternFish(DataInputSource.TEST, 80);    //80 -> 393019

            //Part 2
            day6.countLotsOfLanternFish(DataInputSource.SAMPLE, 80);  // 80 -> 5934
            //day6.countLanternFish(DataInputSource.TEST, 80);    //80 -> 393019


        }
        /**
         * Day 7
         */
        if (dayToRun == 7) {
            Day7 day7 = new Day7();
            System.out.println(day7.getDay7Part1(day7.trialPositions));
            System.out.println(day7.getDay7Part1(day7.originalPoss));
            //Test that accumulator works
            IntStream.range(1, 5).forEach(integer -> System.out.println(day7.getAccumulatorForInt(integer)));

            System.out.println(day7.getDay7Part2(day7.trialPositions));
            // Don't run this by accident as it takes ~20s to complete..
            //
            // System.out.println(day7.getDay7Part2(originalPoss));
        }

        /**
         * Day 8
         */
        if (dayToRun == 8) {
            Day8 day8 = new Day8();
            String entryFile1 = "/Users/gvsmith/Projects/Blackjack/AdventOfCode21/src/data/day8easyEntries";
            String day8Input = "/Users/gvsmith/Projects/Blackjack/AdventOfCode21/src/data/day8Input";

            //day8.readLines(entryFile1);
            day8.readLines(day8Input);
            //day8.printOutLines();
            //day8.getEntriesFor(1);
            //day8.getAllEntries();
            //System.out.println(day8.getMappedLengths(2));
            //day8.getAllMappedLengths();
            //day8.mapLinesToInts();
            //
            //System.out.println("****:");
            //day8.countEntriesWithLengths();
            day8.kickOffPart2();
            //day8.determineMapForLine();
            //day8.assessMappingForLine();
            //day8.getDiff("abcde", "abcd");
        }
        /**
         * Day 9
         */
        if (dayToRun == 9) {
            Day9 day9 = new Day9();
            String day9Input = "/Users/gvsmith/Projects/Blackjack/AdventOfCode21/src/data/day9Input";
            String day9sample = "/Users/gvsmith/Projects/Blackjack/AdventOfCode21/src/data/day9sample";
            //day9.readLines(day9Input);
            day9.readLines(day9sample);
            //Test read in
            day9.printOutLines();
            day9.mapLinesToInts();
            day9.printOutIntegers();
            // PART 1
            //day9.findLowPoints();
            //
            //Part 2

            day9.findBasins();
            day9.printOutBucketNumbersLR();
            day9.printOutBucketNumbers();
            day9.summariseBuckets();
        }

        /** Day 10
         *
         * */
        if (dayToRun == 10) {
            Day10 day10 = new Day10();
            String entrySample = "C:/Users/gary/Dev/advent-of-code/AdventOfCode/src/twentytwentyone/data/day10sample";
            String entryInput = "C:/Users/gary/Dev/advent-of-code/AdventOfCode/src/twentytwentyone/data/day10Input";

            if (partOneOrTwo == 1) {
                // Part 1 trial
                if (inputSource == DataInputSource.SAMPLE.getValue()) {
                    day10.readLines(entrySample, 1);    // Ans = 26397
                } else {
                    day10.readLines(entryInput, 1);     //Ans = 318099
                }
                day10.removeCorrupted();
            } else {
                if (inputSource == DataInputSource.SAMPLE.getValue()) {
                    day10.readLines(entrySample, 1);    // Ans = 288957
                } else {
                    day10.readLines(entryInput, 1);     //Ans = 563570797 => WRONG!
                }
                day10.completeMissing();
            }
        }
    }
}
