package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ExistResponse {

    boolean isExisted;
    Provider snsType;
}
