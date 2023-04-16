package org.project;

import org.project.algorithm.MinimumSpanningTree;
import org.project.entity.Connect;
import org.project.entity.Point;
import org.project.preprocess.CsvReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello world!");
        String csvPath = "C:\\Users\\Asus\\Desktop\\INFO6205_FinalProject\\INFO6205_FinalProject\\info6205.spring2023.teamproject.csv";
        List<Point> csvData = CsvReader.parseData(csvPath);

        List<Connect> connections = MinimumSpanningTree.addConnections(csvData);
    }
}