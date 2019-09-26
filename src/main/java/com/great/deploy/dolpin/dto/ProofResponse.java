package com.great.deploy.dolpin.dto;

public class ProofResponse {

    private boolean result;
    private Long likeCount;

    public ProofResponse(boolean result, Long likeCount) {
        this.result = result;
        this.likeCount = likeCount;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
