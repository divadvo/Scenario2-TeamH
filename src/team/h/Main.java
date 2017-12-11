package team.h;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        new ProblemParser("input/problems.rfp").parse();
        String x= "Hello (Java); (9, 1)";
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(x);
        while(m.find()) {
            System.out.println(m.group(1));
        }
    }
}
