package edu.sdse.csvprocessor;

public class CityRecord {
    int id;
    int year;
    String city;
    int population;

    public String toString(){
        System.out.println("id: " + id + ", year: " + year + ", city: " + city + ", population: " + population);
        return null;
    }
}
