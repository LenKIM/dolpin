package com.great.deploy.dolpin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProofRequest {
    @NotEmpty
    private Long pinId;
    @NotEmpty
    private Integer accountId;

}