package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityCSVProcessor {
	
	public void readAndProcess(File file) {
		//Try with resource statement (as of Java 8)
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			//Discard header row
			br.readLine();
			
			String line;
			List<CityRecord> cityRecordList;
			Map<String, List<CityRecord>> cityMap = new HashMap<>();

			while ((line = br.readLine()) != null) {
				// Parse each line
				String[] rawValues = line.split(",");
				
				int id = convertToInt(rawValues[0]);
				int year = convertToInt(rawValues[1]);
				String city = convertToString(rawValues[2]);
				int population = convertToInt(rawValues[3]);
				
				CityRecord cityRecord = new CityRecord(id, year, city, population);

				if (cityMap.containsKey(cityRecord.city)) {
					cityRecordList = cityMap.get(cityRecord.city);
					cityRecordList.add(cityRecord);
				} else {
					cityRecordList= new ArrayList<>();
					cityRecordList.add(cityRecord);
					cityMap.put(cityRecord.city, cityRecordList);
				}

			}

			for (Map.Entry<String, List<CityRecord>> entry : cityMap.entrySet()) {
				int nrEntries = 0;
				int minYear = Integer.MAX_VALUE;
				int maxYear = 0;
				int avgPop = 0;
			    for (CityRecord record : entry.getValue()) {
					nrEntries++;
					if (record.year < minYear) {
						minYear = record.year;
					}
					if (record.year > maxYear) {
						maxYear = record.year;
					}
					avgPop += record.population;
				}
			    avgPop = avgPop / nrEntries;
			    System.out.println("Average population of " + entry.getKey() + " (" + minYear + "-" + maxYear + ";"
						+ nrEntries + " entries): " + avgPop );
			}


		} catch (Exception e) {
			System.err.println("An error occurred:");
			e.printStackTrace();
		}
	}
	
	private String cleanRawValue(String rawValue) {
		return rawValue.trim();
	}
	
	private int convertToInt(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		return Integer.parseInt(rawValue);
	}
	
	private String convertToString(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		
		if (rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
			return rawValue.substring(1, rawValue.length() - 1);
		}
		
		return rawValue;
	}
	
	public static void main(String[] args) {
		CityCSVProcessor reader = new CityCSVProcessor();
		
		File dataDirectory = new File("data/");
		File csvFile = new File(dataDirectory, "Cities.csv");
		
		reader.readAndProcess(csvFile);
	}
}
