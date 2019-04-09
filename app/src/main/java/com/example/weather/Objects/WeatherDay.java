package com.example.weather.Objects;

public class WeatherDay {
    String Day;
    String Status;
    String Image;
    String MinTemp;
    String MaxTemp;

    public WeatherDay(String day, String status, String image, String minTemp, String maxTemp) {
        Day = day;
        Status = status;
        Image = image;
        MinTemp = minTemp;
        MaxTemp = maxTemp;
    }
}
