package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.ProofResponse;
import com.great.deploy.dolpin.dto.model.Response;
import io.swagger.annotations.ApiModel;


@ApiModel
public class ProofResponseModel extends Response<ProofResponse> {
    public ProofResponseModel(int code, String msg, ProofResponse data) {
        super(code, msg, data);
    }
}
