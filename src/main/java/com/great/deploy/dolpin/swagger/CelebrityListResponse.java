package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.CelebrityResponse;
import com.great.deploy.dolpin.dto.Response;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
public class CelebrityListResponse extends Response<List<CelebrityResponse>> {
    public CelebrityListResponse(int code, String msg, List<CelebrityResponse> data) {
        super(code, msg, data);
    }
}
