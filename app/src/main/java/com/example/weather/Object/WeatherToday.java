package com.example.weather.Object;

public class WeatherToday {
    private String Day;
    private String Status;
    private String MinTemp;
    private String MaxTemp;
    private String ImgStatus;
    private String Humidity;

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

    public WeatherToday(String day, String status, String minTemp, String maxTemp, String imgStatus) {
        Day = day;
        Status = status;
        ImgStatus = imgStatus;
        MinTemp = minTemp;
        MaxTemp = maxTemp;
    }

    public String getImgStatus() {
        return ImgStatus;
    }

    public void setImgStatus(String imgStatus) {
        ImgStatus = imgStatus;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }
}
