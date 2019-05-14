package com.example.weather.Object;

public class Tomorrow24h {
    private String time24h;
    private String icon24h;
    private String temp24h;

    public String getTime24h() {
        return time24h;
    }

    public String getIcon24h() {
        return icon24h;
    }

    public void setTime24h(String time24h) {
        this.time24h = time24h;
    }

    public void setIcon24h(String icon24h) {
        this.icon24h = icon24h;
    }

    public void setTemp24h(String temp24h) {
        this.temp24h = temp24h;
    }

    public String getTemp24h() {
        return temp24h;
    }

    public Tomorrow24h(String time24h, String icon24h, String temp24h) {
        this.time24h = time24h;
        this.icon24h = icon24h;
        this.temp24h = temp24h;
    }
}
