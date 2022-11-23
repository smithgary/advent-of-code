package twentytwentyone;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 extends DataLoader {
    IntStream initialDays;
    Integer daysToRun;
    final List<Lanternfish> lanternfishList = new ArrayList<>();

    public void countLotsOfLanternFish(DataInputSource dataInputSource, Integer daysToRun) {
        // Run out of memory using the original way, need to find a new way.
        this.daysToRun = daysToRun;
        loadInitialValues(dataInputSource);

        List<Integer> initialFish = Arrays.asList(3,4,3,1,2);
        initialFish.forEach(System.out::print); System.out.println();

        List<Integer> fish = initialFish;
        for(int i=0;i<daysToRun; i++) {
           fish = spawn(fish);
        }
        System.out.println("After " + daysToRun + " days there are " + fish.size());
        //List<Integer> newFish = spawn(initialFish);
        //newFish.forEach(System.out::print);System.out.println();

        //List<Integer> newFish1 = spawn(newFish);
    }

    public List<Integer> spawn(List<Integer> initialFish) {

        Long newFish = initialFish.stream().filter(integer -> integer.equals(0)).count();
        Integer noOfNewFish = newFish.intValue();
        List<Integer> newList = initialFish.stream().map(i -> (i == 0) ? 6 : i - 1).collect(Collectors.toList());
        for(int l=0;l<noOfNewFish;l++){
            newList.add(8);
        }
        //newList.forEach(System.out::print);System.out.println();
        return newList;
    }

    //Solution 1
    public void countLanternFish(DataInputSource dataInputSource, Integer daysToRun) {
        this.daysToRun = daysToRun;
        loadInitialValues(dataInputSource);

        for(int i=1; i<daysToRun + 1; i++) {
            List<Lanternfish> newLanternFish = new ArrayList<>();
            lanternfishList.stream().forEach(lanternfish -> {
                lanternfish.dayCompleted();
                if(lanternfish.spawnCycle) {
                    newLanternFish.add(new Lanternfish(8));
                }
            });

            newLanternFish.stream().forEach(lanternfish -> lanternfishList.add(lanternfish));
        }
        System.out.println("After " + daysToRun + " days, there are " + lanternfishList.size());
    }

// Solution 1
    private void loadInitialValues(DataInputSource dataInputSource) {
        loadDataSource(dataInputSource, 1);
        lanternfishList.clear();
        if(dataInputSource.equals(DataInputSource.TEST)){
            String initialChars = column1.get(0);
            String[] a = initialChars.split(",");
            List<Integer> ints = Arrays.stream(a).map(Integer::parseInt).collect(Collectors.toList());
            ints.stream().forEach(initialDay -> lanternfishList.add(new Lanternfish(initialDay)));
        } else {
            initialDays.forEach(initialDay -> lanternfishList.add(new Lanternfish(initialDay)));
        }
    }

    @Override
    protected void loadSampleValues() {
        initialDays = IntStream.of(3,4,3,1,2);
    }
}

class Lanternfish{
    Integer daysUntilNewLanternFish;
    Boolean spawnCycle;
    Boolean firstCycle;

    Lanternfish(Integer initialDays) {
        this.daysUntilNewLanternFish = initialDays;
        this.spawnCycle = false;
    }
    public Integer getDaysUntilNewLanternFish() {
        return daysUntilNewLanternFish;
    }
    public Boolean getSpawnCycle() {
        return spawnCycle;
    }

    public void dayCompleted() {
        if (daysUntilNewLanternFish < 1) {
            daysUntilNewLanternFish = 6;
            spawnCycle = true;
        } else {
            daysUntilNewLanternFish -= 1;
            spawnCycle = false;
        }

    }
}
