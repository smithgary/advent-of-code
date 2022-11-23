package twentytwentyone;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 extends DataLoader{
    private Integer noOfBits;

    // Part 2 solution
    public void getOxygenCO2(DataInputSource dataInputSource) {
        loadDataSource(dataInputSource, 0);
        noOfBits = column1.get(0).length();

        List<String> startingList = column1.stream().collect(Collectors.toList());
        List<String> finalOxygenValue = getSingleEntry(startingList,0, true);
        List<String> finalCO2Value = getSingleEntry(startingList,0, false);

        Integer oxygenValue = Integer.parseInt(finalOxygenValue.get(0), 2);
        Integer co2Value = Integer.parseInt(finalCO2Value.get(0), 2);

        Integer output = oxygenValue * co2Value;
        System.out.println("Final Step2 Calculation for " + dataInputSource + " is : " + output);

    }

    //recursive function
    private List<String> getSingleEntry(List<String> entryList, Integer columnPosition, Boolean isOxygen){
        Integer highest = getHighestCountForColumnFromList(entryList, columnPosition, isOxygen);
        List<String> filteredList = filterListForColumnHaving(entryList, columnPosition, highest);
        if(filteredList.size() == 1) {
            return filteredList;
        } else {
            return getSingleEntry(filteredList, columnPosition + 1, isOxygen);
        }
    }

    private List<String> filterListForColumnHaving(List<String> original, Integer position, Integer highest) {
        final List<String> filteredVersion = original.stream().filter(line -> line.substring(position, position + 1).equals(Integer.toString(highest))).collect(Collectors.toList());
        return filteredVersion;
    }

    // Part 1 solution
    public void getGammaEpsilon(DataInputSource dataInputSource){
        loadDataSource(dataInputSource,0);
        Integer noOfBits = column1.get(0).length();
        List<String> startingList = column1.stream().collect(Collectors.toList());
        Integer[] gamma = new Integer[noOfBits];
        Integer[] epsilon = new Integer[noOfBits];

        for(int i=0; i< noOfBits; i++){
            gamma[i] = getHighestCountForColumnFromList(startingList, i, true);
            epsilon[i] = (getHighestCountForColumnFromList(column1,i, true) == 0) ? 1 : 0;
        }

        Integer epsilonValue = Integer.parseInt(getArrayString(epsilon), 2);
        Integer gammaValue = Integer.parseInt(getArrayString(gamma), 2);

        Integer output = epsilonValue * gammaValue;

        System.out.println("Final Step1 Calculation for " + dataInputSource + " is : " + output);
    }

    private String getArrayString(Integer[] intArray) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<intArray.length;i++){
            sb.append(intArray[i]);
        }
        return sb.toString();
    }

    private Integer getHighestCountForColumnFromList(final List<String> list, final Integer column, final Boolean isOxygen){
        Long total1sCol1 = list.stream()
                .map(strg -> Integer.parseInt(strg, column, column + 1, 2))
                .filter(digit -> digit.equals(1))
                .count();
        Long total0sCol1 = list.stream()
                .map(strg -> Integer.parseInt(strg, column, column + 1, 2))
                .filter(digit -> digit.equals(0))
                .count();

        Integer oxygen = total1sCol1 >= total0sCol1 ? 1 : 0;
        Integer co2 = total0sCol1 <= total1sCol1 ? 0 : 1;

        return isOxygen ? oxygen : co2;

    }

    protected void loadSampleValues() {
        column1.clear();
        column1.add("00100");
        column1.add("11110");
        column1.add("10110");
        column1.add("10111");
        column1.add("10101");
        column1.add("01111");
        column1.add("00111");
        column1.add("11100");
        column1.add("10000");
        column1.add("11001");
        column1.add("00010");
        column1.add("01010");
    }

}
