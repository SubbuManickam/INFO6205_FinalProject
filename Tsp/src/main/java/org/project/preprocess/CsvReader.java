package org.project.preprocess;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.project.entity.Point;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<Point> parseData(String csvPath) {
        try {
            List<Point> csvData = new ArrayList<>();
            Reader csv = new FileReader(csvPath);
            CSVReader csvReader = new CSVReaderBuilder(csv).withSkipLines(1).build();

            String[] lineData;
            while ((lineData = csvReader.readNext()) != null) {
                Point data = new Point();
                data.setCrimeId(lineData[0].substring(lineData[0].length()-5));
                data.setLongitude(Double.valueOf(lineData[1]));
                data.setLatitude(Double.valueOf(lineData[2]));
                csvData.add(data);
            }
            csvReader.close();
            csv.close();

            return csvData;

        } catch (Exception e) {
            return null;
        }
    }
}
