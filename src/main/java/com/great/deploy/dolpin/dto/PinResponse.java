package com.great.deploy.dolpin.dto;

public class PinResponse {
  private Long id;
  private Double latitude;
  private Double longitude;

  public PinResponse(Long id, Double latitude, Double longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
}
