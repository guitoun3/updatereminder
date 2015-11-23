package com.github.guitoun3.updatereminder;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Url;

public interface IApiMethods {

    @GET
    Call<ApiResult> checkUpdate(@Url String url);

}
