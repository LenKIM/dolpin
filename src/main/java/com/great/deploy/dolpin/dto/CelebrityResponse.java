package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.model.CelebrityMember;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CelebrityResponse {
//  private CelebrityMember celebrityMember;
  private Long id;
  private String name;
  private LocalDate birthday;
  private String picUrl;
  private Long groupId;
  private String groupName;

  public CelebrityResponse(){
  }
}
