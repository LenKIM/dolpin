package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.ProofResponse;
import com.great.deploy.dolpin.dto.model.Response;

public class ProofResponseSwagger extends Response<ProofResponse> {
    public ProofResponseSwagger(int code, String msg, ProofResponse data) {
        super(code, msg, data);
    }
}
