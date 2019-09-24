package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.dto.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String email;
    private Provider snsType;
    private String snsId;

}
