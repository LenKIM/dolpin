package com.great.deploy.dolpin.dto;

public class DolpinRequest {
    private String title;
    private String imgUrl;
    private String imgProvider;
    private String latitude;
    private String longitude;

    public DolpinRequest(String title, String imgUrl, String imgProvider, String latitude, String longitude) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.imgProvider = imgProvider;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgProvider() {
        return imgProvider;
    }

    public void setImgProvider(String imgProvider) {
        this.imgProvider = imgProvider;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}


//"{
//        ""title"" : ""재현's 생일 광고판"" ,
//        ""img_url"" : ""https://i.ytimg.com/vi/wXbda9QTOJ0/maxresdefault.jpg"",
//        ""img_provider"" : ""SIMON SAYS"",
//        ""latitude"" : ""37.4992412"",
//        ""longitude"" : ""127.0241753"",
//        ""start_date"" : ""2019-05-02"",
//        ""end_date"" : ""2019-06-30""
//        }"
//
//
