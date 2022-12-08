package twentytwentytwo;

import twentytwentyone.*;

import java.math.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class Day7 extends DataLoader implements AocTest {
    List<BigInteger> directorySizes;

    @Override
    public String calculatePartOne() {
        intialiseDirectoriesAndFiles();

        BigInteger directoriesWithLessThan100k = directorySizes
                .stream()
                .filter(size -> BigInteger.valueOf(100000L).compareTo(size) == 1)
                .reduce(BigInteger.ZERO, BigInteger::add);

        return directoriesWithLessThan100k.toString();
    }

    @Override
    public String calculatePartTwo() {
        BigInteger rootDirSize = intialiseDirectoriesAndFiles();

        BigInteger availableMemory = BigInteger.valueOf(70000000L).subtract(rootDirSize);
        BigInteger memoryToBeDeleted = BigInteger.valueOf(30000000L).subtract(availableMemory);

        BigInteger directorySizeToDelete = directorySizes
                .stream()
                .filter(size -> memoryToBeDeleted.compareTo(size) == -1)
                .sorted()
                .findFirst().get();

        return directorySizeToDelete.toString();
    }

    public BigInteger intialiseDirectoriesAndFiles() {
        directorySizes = new ArrayList<>();
        List<String> startingList = column1.stream().collect(Collectors.toList());
        List<String> commands = startingList.stream().collect(Collectors.toList());

        Directory rootDirectory = loadCommands(commands);

        BigIntCalc b = new BigIntCalc();
        BigInteger rootDirSize = b.getDirectoryFileSize(rootDirectory, directorySizes);
        return rootDirSize;
    }

    public Directory loadCommands(List<String> commands){
        Directory rootDirectory = new Directory();
        rootDirectory.setDirectoryName("Root");
        Directory currentDirectory = rootDirectory;

        for (int i=0; i<commands.size(); i++) {

            Matcher directoryMatch = Pattern.compile("^dir\\s(\\w+)$").matcher(commands.get(i));
            Matcher digitsMatch = Pattern.compile("^([0-9]*)\\s(\\S+)$").matcher(commands.get(i));
            Matcher commandMatch = Pattern.compile("^\\$\\s(\\S+)\\s(\\S+)").matcher(commands.get(i));

            // $ => command, ignore ls, so only have cd .., cd / or cd dirName;
            if (commandMatch.find()) {
                String commandOption1 = commandMatch.group(2);

                Boolean isRoot = false;
                Boolean isGoUp = false;

                if(commandOption1.equals("/")) {
                    isRoot = true;
                }

                if (commandOption1.equals("..")) {
                    currentDirectory = currentDirectory.getParentDirectory();
                    isGoUp = true;
                }

                if (!isRoot && !isGoUp) {
                    Optional<Directory> maybeDirectory = currentDirectory.directories.stream().filter(d -> d.directoryName.equals(commandOption1)).findFirst();
                    if (maybeDirectory.isPresent() && !maybeDirectory.get().directoryName.isEmpty()) {
                        currentDirectory = maybeDirectory.get();
                    }
                }

            }

            // dir => directory
            if (directoryMatch.find()) {
                String directoryName = directoryMatch.group(1);

                Directory directory = new Directory();
                directory.setParentDirectory(currentDirectory);
                directory.setDirectoryName(directoryName);
                currentDirectory.addSubDirectory(directory);
            }

            // digits => filesize, plus filename
            if (digitsMatch.find()) {
                String fileSize = digitsMatch.group(1);
                String fileName = digitsMatch.group(2);

                AOCFile file = new AOCFile(currentDirectory, fileName, new BigInteger(fileSize));
                currentDirectory.addFile(file);
            }
         }
        return rootDirectory;
    }
}

class Directory {
    String directoryName;
    List<Directory> directories;
    Directory parentDirectory;
    List<AOCFile> aocFiles;
    public Directory() {
        directories = new ArrayList<>();
        aocFiles = new ArrayList<>();
    }
    public void addSubDirectory(Directory directory) {
        directories.add(directory);
    }

    public void addFile(AOCFile file) {
        aocFiles.add(file);
    }
    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public List<AOCFile> getAocFiles() {
        return aocFiles;
    }

    public void setAocFiles(List<AOCFile> aocFiles) {
        this.aocFiles = aocFiles;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }
}

class AOCFile {
    Directory parentDirectory;
    String fileName;
    BigInteger fileSize;

    public AOCFile(Directory parentDirectory, String fileName, BigInteger fileSize) {
        this.parentDirectory = parentDirectory;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }
}

class BigIntCalc {
    public BigInteger getDirectoryFileSize(Directory directory, List<BigInteger> directorySizes) {

        BigInteger directorySize = BigInteger.ZERO;

        if (directory.getDirectories().size() > 0) {
            BigInteger subDirectorySize = directory
                    .getDirectories()
                    .stream()
                    .map(d -> {
                        BigIntCalc b = new BigIntCalc();
                        return b.getDirectoryFileSize(d, directorySizes);
                    })
                    .reduce(BigInteger.ZERO, BigInteger::add);
            directorySize = directorySize.add(subDirectorySize);
        }

        BigInteger maybeFileSize = directory.getAocFiles()
                .stream()
                .map(file -> file.fileSize)
                .reduce(BigInteger.ZERO, BigInteger::add);

        directorySize = directorySize.add(maybeFileSize);

        directorySizes.add(directorySize);

        return directorySize;
    }
}