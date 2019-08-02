package com.great.deploy.dolpin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PinResponse {
  private Long id;
  private Double latitude;
  private Double longitude;
}
