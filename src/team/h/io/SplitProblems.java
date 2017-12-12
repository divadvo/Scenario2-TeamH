package team.h.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class SplitProblems {

    private String problemFilePath;
    private String problemFolderPath;

    public SplitProblems(String problemFilePath, String problemFolderPath) {
        this.problemFilePath = problemFilePath;
        this.problemFolderPath = problemFolderPath;
    }

    public void split() {
        try {
            List<String> problemStrings = Files.lines(Paths.get(problemFilePath)).collect(Collectors.toList());
            for (String line : problemStrings) {
                Path solutionOutputFilePath = Paths.get(problemFolderPath, "problem" + getProblemNumber(line) + ".rfp");
                Files.write(solutionOutputFilePath, line.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getProblemNumber(String problemString) {
        int colon = problemString.indexOf(":");
        return Integer.parseInt(problemString.substring(0, colon).trim());
    }

    public static void main(String[] args) {
        new SplitProblems("input/problems.rfp", "input/").split();
    }

}
