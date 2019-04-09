package com.example.weather.Object;

public class WeatherToday {
    String Day;
    String Status;
    String MinTemp;
    String MaxTemp;

    public String getDay() {
        return Day;
    }

    public String getStatus() {
        return Status;
    }

    public String getMinTemp() {
        return MinTemp;
    }

    public String getMaxTemp() {
        return MaxTemp;
    }

    public void setDay(String day) {
        Day = day;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setMinTemp(String minTemp) {
        MinTemp = minTemp;
    }

    public void setMaxTemp(String maxTemp) {
        MaxTemp = maxTemp;
    }

    public WeatherToday(String day, String status, String minTemp, String maxTemp) {
        Day = day;
        Status = status;

        MinTemp = minTemp;
        MaxTemp = maxTemp;
    }
}
