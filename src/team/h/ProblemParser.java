package team.h;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemParser {

    private String problemFilePath;
    private List<String> problems;

    public ProblemParser(String problemFilePath) {
        this.problemFilePath = problemFilePath;
    }

    public void parse() {
        try {
            problems = Files.lines(Paths.get(problemFilePath)).collect(Collectors.toList());
            System.out.println(problems.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
