package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.domain.Visit;

public class ProofResponse {

    private boolean result;

    public ProofResponse(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
