package com.wishland.www.xinwanhaotest.api;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/4/20.
 */

public class HttpApiInstance extends BaseApi {
    public HttpApiInstance(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public ApiAddress getObservable(Retrofit retrofit) {
        return retrofit.create(ApiAddress.class);
    }
}
