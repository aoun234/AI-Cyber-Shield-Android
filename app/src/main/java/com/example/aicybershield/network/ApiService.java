package com.example.aicybershield.network;

import com.example.aicybershield.models.ScanResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("scan")
    Call<ScanResponse> scanWebsite(@Body JsonObject body);

    @POST("chat")
    Call<JsonObject> chat(@Body JsonObject body);
}