package com.great.deploy.dolpin.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePinRequest {
  private Long memberId;
  private Long groupId;
  private String title;
  private String imgProvider;
  private Double latitude;
  private Double longitude;
  private LocalDate startDate;
  private LocalDate endDate;
}
