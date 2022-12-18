package twentytwentytwo;

import twentytwentyone.AocTest;
import twentytwentyone.DataLoader;
import util.Utilities;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day11 extends DataLoader implements AocTest {
    List<Monkey> monkeys;

    @Override
    public String calculatePartOne() {
        monkeys = new ArrayList<>();
        loadMonkeys();

        for(int i=0;i<20;i++) {
            processMonkeyBusiness(false);
            //System.out.println((i + 1) + " Rounds");
            for(int j=0;j<monkeys.size();j++) {
                //System.out.print("Monkey " + j + ":");
                //monkeys.get(j).getItems().forEach(item-> System.out.print(item + ","));
                //System.out.println();
            }
            //System.out.println();
        }

        for(int j=0;j<monkeys.size();j++) {
            //System.out.println("Monkey " + j + " inspected items " + monkeys.get(j).getOperationCount() + " times.");
        }

        List<Integer> topTwoMonkeys = monkeys.stream()
                .map(monkey -> monkey.getOperationCount())
                .sorted(Collections.reverseOrder())
                .limit(2L)
                .collect(Collectors.toList());
        Integer shennanigans = topTwoMonkeys.get(0) * topTwoMonkeys.get(1);

        return shennanigans.toString();
    }

    @Override
    public String calculatePartTwo() {
        // Need to change to BigInteger or Long ..
        monkeys = new ArrayList<>();
        loadMonkeys();

        for (int i = 0; i < 1000; i++) {
            processMonkeyBusiness(true);
            if ((i+1) % 10 == 0) {
                System.out.println((i + 1) + ": Monkey 1: inspected items " + monkeys.get(0).getOperationCount() + " times.");
            }

        }
//        for(int j=0;j<monkeys.size();j++) {
//            if (j % 100 == 0) {
//                System.out.println("Monkey " + j + " inspected items " + monkeys.get(j).getOperationCount() + " times.");
//            }
//        }

        List<Integer> topTwoMonkeys = monkeys.stream()
                .map(monkey -> monkey.getOperationCount())
                .sorted(Collections.reverseOrder())
                .limit(2L)
                .collect(Collectors.toList());
        Integer shennanigans = topTwoMonkeys.get(0) * topTwoMonkeys.get(1);

        return shennanigans.toString();

    }

    public void processMonkeyBusiness(Boolean isPartTwo) {
        for(int i=0; i<monkeys.size(); i++) {
            Monkey monkey = monkeys.get(i);
            Integer startingItemSize = monkey.getItems().size();
            for (int j=0;j< startingItemSize; j++) {
                Long worryLevel = monkey.getItems().get(j);
                Long newWorryLevel = monkey.operation.operate(worryLevel);
                monkey.setOperationCount(monkey.getOperationCount() + 1);
                Long boredLevel = newWorryLevel;
                if(!isPartTwo) {
                    boredLevel = newWorryLevel / 3;
                }
                Long divisor = monkey.getTest().getDivisor();
                Boolean isDividable = boredLevel % divisor == 0;
                if (isDividable) {
                    monkeys.get(monkey.getTest().getReceivingMonkeyOnPass()).getItems().add(boredLevel);
                } else {
                    monkeys.get(monkey.getTest().getReceivingMonkeyOnFail()).getItems().add(boredLevel);
                }
            }
            monkey.getItems().subList(0, startingItemSize).clear();

        }
    }
    public void loadMonkeys(){
        List<String> startingList = column1.stream().collect(Collectors.toList());

        Integer currentMonkeyId = 0;
        for (int i=0;i<startingList.size();i++) {
            String[] line = startingList.get(i).split( " ");

            if (line[0].equals("Monkey")) {
                Monkey monkey = new Monkey();
                Integer monkeyId = Integer.valueOf(line[1].split("\\:")[0]);
                monkey.setId(monkeyId);
                monkey.setOperationCount(0);
                monkeys.add(monkey);
                currentMonkeyId = monkeyId;
            }
            if (line.length > 2) {
                if (line[2].equals("Operation:")) {
                    String[] change = new String[2];
                    change[0] = line[6];
                    change[1] = line[7];
                    OperationBuilder operationBuilder = new OperationBuilder(change, 0L);
                    Operation operation = operationBuilder.build(0L, change);
                    monkeys.get(currentMonkeyId).setOperation(operation);
                }
                if (line[2].equals("Starting")) {
                    List<Long> startingItems = new ArrayList<>();
                    for(int j=4; j<line.length; j++) {
                        String startingItemStr = line[j].replace(",", "").trim();
                        startingItems.add(Long.valueOf(startingItemStr));
                    }
                    monkeys.get(currentMonkeyId).setItems(startingItems);
                }
                if (line[2].equals("Test:")) {
                    String denominator = line[5];
                    Test test = new Test(Long.valueOf(denominator));
                    monkeys.get(currentMonkeyId).setTest(test);
                }
                if (line.length > 5) {
                    if (line[5].equals("true:")) {
                        Integer receivingMonkey = Integer.parseInt(line[9]);
                        monkeys.get(currentMonkeyId).getTest().setReceivingMonkeyOnPass(receivingMonkey);
                    }
                    if (line[5].equals("false:")) {
                        Integer receivingMonkey = Integer.parseInt(line[9]);
                        monkeys.get(currentMonkeyId).getTest().setReceivingMonkeyOnFail(receivingMonkey);
                    }
                }
            }
        }
    }
}


class Monkey {
    private Integer Id;
    List<Long> items;
    Operation operation;
    Integer operationCount;

    public Test test;

    public void setId(Integer id) {
        Id = id;
    }

    public List<Long> getItems() {
        return items;
    }

    public void setItems(List<Long> items) {
        this.items = items;
    }


    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Integer getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(Integer operationCount) {
        this.operationCount = operationCount;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}

interface Operation {
    public Long operate(Long old);
}

class OperationBuilder {
    public OperationBuilder(String[] change, Long old) {
        build(old, change);
    }
    public Operation build(Long old, String[] change){
       return new Operation() {
           @Override
           public Long operate(Long old) {
               Long value = 0L;
               Boolean usingOld = false;
               if (change[1].equals("old")) {
                       usingOld = true;
               } else {
                   value = Long.valueOf(change[1]);
               }
               if(change[0].equals("+")) {
                   return old + (usingOld ? old : value);
               }
               if(change[0].equals("-")) {
                   return old - (usingOld ? old : value);
               }
               if(change[0].equals("*")) {
                   return old * (usingOld ? old : value);
               }
               return 0L;
               //return BigInteger.ZERO;
           }
       };
    }
}

class Test {
    private Long divisor;
    private Integer receivingMonkeyOnFail;
    private Integer receivingMonkeyOnPass;
    public Test(Long divisor) {
        this.divisor = divisor;
    }

    public Long getDivisor() {
        return divisor;
    }

    public void setDivisor(Long divisor) {
        this.divisor = divisor;
    }

    public Integer getReceivingMonkeyOnFail() {
        return receivingMonkeyOnFail;
    }

    public void setReceivingMonkeyOnFail(Integer receivingMonkeyOnFail) {
        this.receivingMonkeyOnFail = receivingMonkeyOnFail;
    }

    public Integer getReceivingMonkeyOnPass() {
        return receivingMonkeyOnPass;
    }

    public void setReceivingMonkeyOnPass(Integer receivingMonkeyOnPass) {
        this.receivingMonkeyOnPass = receivingMonkeyOnPass;
    }
}
