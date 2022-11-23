package twentytwentyone;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class DataLoader {
    public ArrayList<String> column1 = new ArrayList<>();
    ArrayList<Integer> column2 = new ArrayList<>();

    String fileName = "";

    protected void loadDataSource(DataInputSource dataInputSource, Integer sourceType) {
        if (dataInputSource == DataInputSource.SAMPLE) {
            loadSampleValues();
        }
        if (dataInputSource == DataInputSource.TEST) {
            readLines(fileName, sourceType);
        }
    }
    protected void loadSampleValues() {
        column1.clear();
        column1.add("forward");
        column1.add("down");
        column1.add("forward");
        column1.add("up");
        column1.add("down");
        column1.add("forward");

        column2.clear();
        column2.add(5);
        column2.add(5);
        column2.add(8);
        column2.add(3);
        column2.add(8);
        column2.add(2);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void readLines(String fileName, Integer inputType) {
        column1.clear();
        column2.clear();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                if(inputType == 0) {
                    column1.add(line.split(Pattern.quote(" "))[0]);
                    //column2.add(Integer.parseInt(line.split(Pattern.quote(" "))[1].trim()));
                }
                if(inputType == 1) {
                    column1.add(line);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
