package com.example.weather.Object;

public class Thoitiet24h {
    private String Time24h;
    private String Icon24h;
    private String Nhietdo24h;

    public String getTime24h() {
        return Time24h;
    }

    public void setTime24h(String time24h) {
        Time24h = time24h;
    }

    public String getIcon24h() {
        return Icon24h;
    }

    public void setIcon24h(String icon24h) {
        Icon24h = icon24h;
    }

    public String getNhietdo24h() {
        return Nhietdo24h;
    }

    public void setNhietdo24h(String nhietdo24h) {
        Nhietdo24h = nhietdo24h;
    }

    public Thoitiet24h(String time24h, String icon24h, String nhietdo24h) {
        Time24h = time24h;
        Icon24h = icon24h;
        Nhietdo24h = nhietdo24h;
    }
}
