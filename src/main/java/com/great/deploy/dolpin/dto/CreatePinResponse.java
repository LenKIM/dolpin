package com.great.deploy.dolpin.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CreatePinResponse {
  private Long id;
  private String title;
  private String imgUrl;
  private String imgProvider;
  private Double latitude;
  private Double longitude;
  private LocalDate startDate;
  private LocalDate endDate;
}
