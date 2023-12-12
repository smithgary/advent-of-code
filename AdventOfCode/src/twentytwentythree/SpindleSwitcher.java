package twentytwentythree;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.*;

public class SpindleSwitcher {
    // load data
    private final String recipeFile = "C:/Share/Product Recipes.CSV";
    private String newFileName;
    public static final String FAIL = "0";
    public static final Integer REARRANGE_SUCCEEDED = 0;
    public static final int PRODUCTFILENOTEXISTS = 1;
    public static final int PRODUCTFILEREADERROR = 2;
    public static final int RECIPEFILENOTEXISTS = 3;
    public static final int RECIPEFILEREADERROR = 4;
    public static final int NOMAPPEDDRIVE = 5;
    public static final int POSITIONOFSECTION = 6;
    public static final int NO_AVAILABLE_HEADS = 7;
    public static final int OUTPUT_FILE_WRITE_ERROR = 8;
    public static final int RECIPE_NOT_FOUND = 9;
    public static final int TOO_MANY_PATTERNS = 10;
    public static final int NO_HEADS_SELECTED = 11;
    private ArrayList<String> recipeData;
    private List<RecipeLine> recipeLines;
    private List<RecipeLine> outputWorkOrder;
    private String productCode;
    List<Integer> inactiveSpindles;
    private Boolean reallocateFailed;
    private Integer reallocateErrorCode;
    private String reallocateErrorMsg;
    SpindleSwitcherUI ui;

    public SpindleSwitcher() {
        displayOptions();
    }

    public void displayOptions() {
        ui = SpindleSwitcherUI.getInstance();
        ui.displayOptions(this);
    }

    public void grabProductCode() {
        // Set Product code entered by user
        productCode = ui.getFormLabels().get("Load Product Code").getText(); //"416999901";
    }

    public List<Integer> loadInactiveSpindles() {
        List<Integer> inactiveSpindles = rangeClosed(1, 12)
                .filter(i -> !ui.getFormToggleButtons().get("Spindle " + i).isSelected())
                .boxed()
                .collect(toList());

        if (inactiveSpindles.isEmpty()) {
            markAsFailed(NO_HEADS_SELECTED, "No heads selected");
            // Not really a failure, can continue with no heads disabled - will just be the same result
            return Collections.emptyList();
        }
        StringBuilder sb = new StringBuilder();
        inactiveSpindles.forEach(i -> sb.append(i + ","));
        String list = sb.toString().substring(0, sb.length()-1);
        String output = "Spindles " + list + " disabled. Now re-arranging...";
        ui.updateTextArea(output, ui.getOutputTextArea());
        return inactiveSpindles;
    }

    public Integer getInitialSettings() {
        // Todo do something with this int.
        grabProductCode();
        loadRecipeInfo();
        if (reallocateFailed) {
            return getReallocatedErrorCode();
        }
        summariseConeLocations(recipeLines);
        return 1;
    }

    public void applyChanges(){
        // Set Product code entered by user
        grabProductCode();
        // Load and set inactive spindles
        List<Integer> inactiveSpindles = loadInactiveSpindles();
        setInactiveSpindles(inactiveSpindles);

        // Perform rearrange , get status as result.
        Integer result = reviseRecipeFile();
        String resultText = REARRANGE_SUCCEEDED.equals(result) ? "Success" : getReallocateErrorMsg();
        String finalLine = ": Result-> " + resultText;
        if (REARRANGE_SUCCEEDED.equals(result)) {
            for (RecipeLine line : outputWorkOrder){
                ui.updateTextArea(line.toString(), ui.getOutputTextArea());
            }
        }
        ui.updateTextArea(finalLine, ui.getOutputTextArea());
        summariseConeLocations(outputWorkOrder);
    }

    public Integer loadRecipeInfo() {
        reallocateFailed = false;
        newFileName = "C:/Share/Product Recipes_Redistributed.CSV";

        // Load original CSV file into recipeData
        loadRecipeFile();
        if (reallocateFailed) {
            return getReallocatedErrorCode();
        }
        recipeLines = new ArrayList<>();
        recipeData.stream().forEach(r -> setRecipeLine(r));
        if (recipeLines.size() < 1) {
            markAsFailed(RECIPE_NOT_FOUND, String.format("Requested recipe %s not found in settings file", productCode));
            return getReallocatedErrorCode();
        }
        return 1;
    }

    public void summariseConeLocations(List<RecipeLine> coneList) {

        // Build a comma separated String for each Spindle (head), total patterns per section, ignore group.
        Map<Integer, Map<String, List<String>>> asdf = new HashMap<>();

        rangeClosed(1,12)
                .forEach(i -> {
                    Map<String, List<String>> collect = coneList.stream().filter(cl -> cl.getHead().equals(Integer.toString(i)))
                            .collect(groupingBy(RecipeLine::getSection, mapping(RecipeLine::getPattern, toList())));
                    asdf.put(i, collect);
                });

        System.out.println("");
        Map<Integer, String> colourStatusForHead = new HashMap<>();
        asdf.entrySet().stream().forEach(es -> {
            Integer headNumber = es.getKey();
            Map<String, List<String>> pattersPerSectionForHead = es.getValue();
            StringBuilder stringBuilder = new StringBuilder();
            pattersPerSectionForHead.entrySet().stream()
                    .forEach(sect -> {
                        String sectName = sect.getKey();
                        List<String> patterns = sect.getValue();
                        Integer patternCount = patterns.size();
                        Set<String> uniquePatterns = new HashSet<>(patterns);
                        // Check to ensure all same pattern (colour)
                        if (uniquePatterns.size() > 1) {
                            markAsFailed(TOO_MANY_PATTERNS, String.format("More than one pattern found for head %s in section %s", headNumber, sectName));
                            stringBuilder.append(sectName + " !!> 1; ");
                        }
                        String pattern = uniquePatterns.iterator().next();
                        stringBuilder.append(sectName + " : " + pattern + "(" + patternCount + "); ");
                    });
            colourStatusForHead.put(headNumber, stringBuilder.toString());
        });

        colourStatusForHead.entrySet().stream()
                .forEach(head -> ui.getFormLabels().get("Spindle " + head.getKey() + "out").setText(head.getValue()));

    }
    public Integer reviseRecipeFile() {
        loadRecipeInfo();
        if (reallocateFailed) {
            return getReallocatedErrorCode();
        }
        // Rearrange items
        List<RecipeLine> rearrangedList = getRearrangedList();
        if (reallocateFailed) {
            return getReallocatedErrorCode();
        }
        // Sort and Print out (to console) rearranged List
        sortAndPrintListOfCones(rearrangedList);
        // Store as CSV file
        try {
            createCSV(outputWorkOrder);
        } catch (IOException ioException) {
            markAsFailed(OUTPUT_FILE_WRITE_ERROR, "Unable to write to file " + ioException.getMessage());
            return OUTPUT_FILE_WRITE_ERROR;
        }
        return REARRANGE_SUCCEEDED;
    }

    public void sortAndPrintListOfCones(List<RecipeLine> workOrderList) {
        List<RecipeLine> sortedWorkOrder = workOrderList.stream().sorted(Comparator.comparing(RecipeLine::getGroup)
                        .thenComparing(RecipeLine::getSection)
                        .thenComparing(RecipeLine::getNumberAsInt))
                .collect(toList());

        sortedWorkOrder.stream().forEach(wo -> System.out.println(wo));
        outputWorkOrder = sortedWorkOrder;
    }

    public List<RecipeLine> getRearrangedList() {

        List<RecipeLine> conesToBeMoved = getCones(true);
        List<RecipeLine> conesAlreadyInPlace = getCones(false);
        for (RecipeLine cone : conesToBeMoved) {
            // Find head with the lowest count that has same section and pattern
            String newHead = getHeadWithMostCapacityFor(cone, conesAlreadyInPlace);
            if (FAIL.equals(newHead)) {
                // Didn't find a head to put this cone onto, ie none with the correct colour and section
                break;
            }
            RecipeLine revised = cone.getCopy();
            revised.setHead(newHead);
            conesAlreadyInPlace.add(revised);
        }

        return conesAlreadyInPlace;
    }

    public String getHeadWithMostCapacityFor(RecipeLine recipeLine, List<RecipeLine> headsAlreadyLoaded){
        // Get list of possible spindle destinations, ie
        // is active and has the same group, section and pattern as that which is to be reallocated
        List<RecipeLine> possiblePlaces = headsAlreadyLoaded.stream()
                .filter(rl -> !inactiveSpindles.contains(Integer.valueOf(rl.getHead())))
                .filter(rl -> recipeLine.getGroup().equals(rl.getGroup()))
                .filter(rl -> recipeLine.getSection().equals(rl.getSection()))
                .filter(rl -> recipeLine.getPattern().equals(rl.getPattern()))
                .collect(Collectors.toList());

        if (possiblePlaces.size() < 1) {
            markAsFailed(NO_AVAILABLE_HEADS, String.format("No heads available to transfer cone %s onto", recipeLine.getNumber()));
            return FAIL;
        }

        // Take the list above, group by spindle, with a list of the uptake values per spindle
        Map<String, List<String>> headsCurrentUpdateBurden = possiblePlaces.stream().collect(
                groupingBy(RecipeLine::getHead, mapping(RecipeLine::getUptake, toList())));


        // Create a map of spindle and sum of uptake values
        Map<String, Double> headUsageForSection = new HashMap<>();
        headsCurrentUpdateBurden.entrySet().stream().forEach(k -> {
            headUsageForSection.put(k.getKey(),
            k.getValue().stream().mapToDouble(str -> Double.valueOf(str))
                    .reduce(Double::sum)
                    .orElse(0.0));
        });

        // Sort the map by sum of uptake values - get the first (lowest) key (ie head aka spindle)
        String key = headUsageForSection.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .findFirst()
                .get()
                .getKey();
        
        return key;
    }

    private List<RecipeLine> getCones(Boolean toBeMoved) {
        // Create copy to avoid mutating original file.
        List<RecipeLine> workOrder = getCopyOf(recipeLines);
        // Get list of RecipeLines associated with inactive spindles
        List<RecipeLine> conesToBeMoved = workOrder.stream()
                .filter(rl -> toBeMoved
                        ? inactiveSpindles.contains(Integer.valueOf(rl.getHead()))
                        : !inactiveSpindles.contains(Integer.valueOf(rl.getHead())))
                .sorted(Comparator.comparing(RecipeLine::getHead)
                        .thenComparing(RecipeLine::getSection)
                        .thenComparing(RecipeLine::getPattern)
                        .thenComparing(RecipeLine::getNumber))
                .collect(Collectors.toList());

        conesToBeMoved.stream().forEach(r -> System.out.println(r));
        return conesToBeMoved;
    }

    public void setRecipeLine(String csvLine) {
        StringTokenizer tokens = new StringTokenizer(csvLine, ",");
        RecipeLine r = new RecipeLine();
        r.setNumber(tokens.nextToken());
        r.setPattern(tokens.nextToken());
        r.setUptake(tokens.nextToken());
        r.setGroup(tokens.nextToken());
        r.setHead(tokens.nextToken());
        r.setSection(tokens.nextToken());
        r.setProduct(tokens.nextToken());
        r.setRecall(tokens.nextToken());

        if (this.productCode.equals(r.getProduct())) {
            recipeLines.add(r);
        }
    }

    public int loadRecipeFile() {
        int status = REARRANGE_SUCCEEDED;
        Path path = null;
        // get the file
        try{
            // create file pointer
            path = Paths.get(recipeFile);
            if(!path.toFile().exists()){
                markAsFailed(RECIPEFILENOTEXISTS, String.format("File not found: %s", recipeFile));
                return RECIPEFILENOTEXISTS;
            }
        }
        catch(Exception e){
            // any exceptions return file doesn't exist
            markAsFailed(RECIPEFILENOTEXISTS, String.format("Exception - File not found: %s, %s", recipeFile, e.getMessage()));
            return RECIPEFILENOTEXISTS;
        }
        // file exists read it in and store all recipes n memory, to be processed when the
        // product and section is known
        try{
            Stream<String> stream = Files.lines(path);
            recipeData = (ArrayList<String>)stream.map(l -> l.replaceAll("\"", "")).collect(Collectors.toList());
        }
        catch (Exception e){
            // any unexpected exceptions are read error
            markAsFailed(RECIPEFILEREADERROR, String.format("Exception - File read error: %s, %s", recipeFile, e.getMessage()));
            return RECIPEFILEREADERROR;
        }
        return status;
    }
    
    public List<RecipeLine> getCopyOf(List<RecipeLine> thatList) {
        // Get a safe immutable copy of an entire list
        List<RecipeLine> thisList = new ArrayList<>();
        thatList.stream().forEach(that -> {
            RecipeLine recipeLine = that.getCopy();
            thisList.add(recipeLine);
        });
        return thisList;
    }

    public void createCSV(List<RecipeLine> rearrangedList) throws IOException {
        File csvOutput = new File(newFileName);
        try (PrintWriter pw = new PrintWriter(csvOutput)) {
            // Print header line
            RecipeLine empty = new RecipeLine();
            String header = this.convertToCSV(empty.getHeader());
            pw.println(header);
            // Print all the data
            rearrangedList.stream()
                    .map(rl -> this.convertToCSV(rl.toArray()))
                    .forEach(pw::println);
        }
    }
    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public void markAsFailed(Integer errorCode, String logMsg){
        reallocateFailed = true;
        this.reallocateErrorCode = errorCode;
        this.reallocateErrorMsg = logMsg;
        System.out.println(logMsg);
    }

    private Integer getReallocatedErrorCode() {
        return reallocateErrorCode;
    }
    private String getReallocateErrorMsg() {
        return reallocateErrorMsg;
    }

    public void setRecipeLines(List<RecipeLine> recipeLines) {
        this.recipeLines = recipeLines;
    }

    public void setInactiveSpindles(List<Integer> inactiveSpindles) {
        this.inactiveSpindles = inactiveSpindles;
    }

}
