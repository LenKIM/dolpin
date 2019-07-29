package com.great.deploy.dolpin.dto;

public class ProofResponse {

    boolean result;

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
