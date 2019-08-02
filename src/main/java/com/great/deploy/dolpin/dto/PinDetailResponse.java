package com.great.deploy.dolpin.dto;

import java.time.LocalDate;

public class PinDetailResponse {
  private Long id;
  private String title;
  private String imgUrl;
  private String imgProvider;
  private LocalDate startDate;
  private LocalDate endDate;

  public PinDetailResponse(Long id, String title, String imgUrl, String imgProvider, LocalDate startDate, LocalDate endDate) {
    this.id = id;
    this.title = title;
    this.imgUrl = imgUrl;
    this.imgProvider = imgProvider;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
