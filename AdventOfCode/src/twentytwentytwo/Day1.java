package twentytwentytwo;

import twentytwentyone.*;
import util.*;

import java.util.*;

import static java.util.Collections.emptyList;

public class Day1 extends DataLoader implements AocTest {
    Utilities utilities = new Utilities();

    @Override
    public String calculatePartOne() {

        List<List<String>> groupedCalories = getGroupedCalories();

        Optional<Integer> maxCalories = groupedCalories.stream()
                .map(x -> utilities.getListOfIntegersFromListOfString(x))
                .map(x -> x.stream().reduce(Integer::sum).get())
                .reduce(Integer::max);

        return maxCalories.isPresent() ? maxCalories.get().toString() : "0";
    }


    @Override
    public String calculatePartTwo() {
        List<List<String>> groupedCalories = getGroupedCalories();

        Optional<Integer> sumOfTop3CalorieValues = groupedCalories.stream()
                .map(x -> utilities.getListOfIntegersFromListOfString(x))
                .map(x -> x.stream().reduce(Integer::sum).get())
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(Integer::sum);

        return sumOfTop3CalorieValues.isPresent() ? sumOfTop3CalorieValues.get().toString() : "0";
    }

    public List<List<String>> getGroupedCalories() {
        List<List<String>> initial = new ArrayList<>();
        initial.add(new ArrayList<>());

        return column1.stream().reduce(initial, (subtotal, element) -> {
            if (element.trim().isEmpty()) {
                subtotal.add(new ArrayList<>());
            } else {
                subtotal.get(subtotal.size() - 1).add(element);
            }
            return subtotal;

        }, (list1, list2) -> emptyList());
    }
}
