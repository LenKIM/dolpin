package com.great.deploy.dolpin.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PinDetailResponse {
  private Long id;
  private String title;
  private String imgUrl;
  private String imgProvider;
  private LocalDate startDate;
  private LocalDate endDate;
}
