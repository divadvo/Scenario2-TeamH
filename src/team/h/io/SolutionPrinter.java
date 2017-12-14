package team.h.io;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;
import team.h.core.Point;
import team.h.core.Shape;
import team.h.core.Solution;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SolutionPrinter {

    private String solutionFolderPath;
    private List<Solution> solutions;

    private static final String TEAM = "granada";
    private static final String PASSWORD = "5u0op320foc1di7eov3vkl0349";
    private String fileName;
    private Path solutionOutputFilePath;


    public SolutionPrinter(String solutionFolderPath, List<Solution> solutions) {
        this.solutionFolderPath = solutionFolderPath;
        this.solutions = solutions;
    }

    public void output() {
        DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("YYYY-MM-dd_HH-mm-ss");
        String time = timeStampPattern.format(java.time.LocalDateTime.now());
//        String time = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        fileName = String.format("%s_%.2f_%.2f.solutions", time, totalCost(), totalArea());

        solutionOutputFilePath = Paths.get(solutionFolderPath, fileName);

        List<String> lines = new ArrayList<>();
        lines.add(TEAM);
        lines.add(PASSWORD);
        for(Solution solution : solutions) {
            lines.add(solutionToString(solution));
        }

        try {
            Files.write(solutionOutputFilePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String solutionToString(Solution solution) {
        StringBuilder result = new StringBuilder();
        result.append(solution.getSolutionNumber() + ": ");

        for (Shape shape : solution.getShapes()) {
            result.append(shapeToString(shape));
        }

        // remove last semicolon
//        result.setLength(result.length() - 1);

//        System.out.println(result.toString());
        return removeLastChar(result.toString());
    }

    private String removeLastChar(String str) {
        return str.substring(0, str.length() - 2);
    }

    private String shapeToString(Shape shape) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < shape.getPoints().size(); i++) {
            Point point = shape.getPoints().get(i);

            String pointString = String.format("(%.15f,%.15f)", point.getX(), point.getY());
            result.append(pointString);


            if(i == shape.getPoints().size() - 1) // if last -> add semicolon
                result.append("; ");
            else // otherwise comma and space
                result.append(", ");
        }

        return result.toString();
    }

    public double totalCost() {
        double cost = 0;
        for(Solution solution : solutions)
            cost += solution.getTotalCost();
        return cost;
    }

    public double totalArea() {
        double area = 0;
        for(Solution solution : solutions)
            area += solution.getTotalArea();
        return area;
    }

    public void upload() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://scenario.cs.ucl.ac.uk/upload");

            FileBody bin = new FileBody(new File(solutionOutputFilePath.toUri()));

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", bin)
                    .build();


            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " +    resEntity.getContentLength());
//                    String content = EntityUtils.toString(resEntity);
//                    System.out.println("Response: " +  content);
                }
                EntityUtils.consume(resEntity);
            } catch (Exception e) {

            } finally {
                response.close();
            }
            httpclient.close();
        } catch (Exception e) {

        }
    }
}
