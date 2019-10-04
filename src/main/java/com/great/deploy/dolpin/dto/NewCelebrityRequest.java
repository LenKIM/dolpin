package com.great.deploy.dolpin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NewCelebrityRequest {
    @NotEmpty
    String groupName;
    @NotEmpty
    List<String> memberList;
}
