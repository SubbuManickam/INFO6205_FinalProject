package org.project;

import org.project.preprocess.CsvReader;
import org.project.preprocess.Data;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        String csvPath = "C:\\Users\\Asus\\Desktop\\INFO6205_FinalProject\\INFO6205_FinalProject\\info6205.spring2023.teamproject.csv";
        List<Data> csvData = CsvReader.parseData(csvPath);

        for(Data data : csvData) {
            System.out.println(data.getCrimeId() + " - " + data.getLatitude() + " , " + data.getLongitude());
        }
    }
}