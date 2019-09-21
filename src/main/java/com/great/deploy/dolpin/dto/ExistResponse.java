package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExistResponse {

    private boolean isExisted;
    private Provider snsType;
}
