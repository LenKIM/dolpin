package com.great.deploy.dolpin.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PinInfo {
  private String title;
  private String imgUrl;
  private String imgProvider;
  private Double latitude;
  private Double longitude;
  private LocalDate startDate;
  private LocalDate endDate;
}
