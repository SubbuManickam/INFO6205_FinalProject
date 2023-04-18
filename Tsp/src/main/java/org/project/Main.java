package org.project;

import org.project.algorithm.Christofides;
import org.project.algorithm.GreedyAlgorithm;
import org.project.entity.Point;
import org.project.preprocess.CsvReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Travelling Salesman Problem and Optimizations");
        String csvPath = "C:\\Users\\Asus\\Desktop\\INFO6205_FinalProject\\INFO6205_FinalProject\\info6205.spring2023.teamproject.csv";
        List<Point> csvData = CsvReader.parseData(csvPath);

        Christofides.tsp(csvData);
        GreedyAlgorithm.tsp(csvData);
    }
}