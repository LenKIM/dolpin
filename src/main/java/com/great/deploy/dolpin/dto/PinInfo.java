package com.great.deploy.dolpin.dto;

import java.time.LocalDate;

public class PinInfo {
  private String title;
  private String imgUrl;
  private String imgProvider;
  private Double latitude;
  private Double longitude;
  private LocalDate startDate;
  private LocalDate endDate;

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

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
}
