package com.sajalnarang.loginregisterdemo;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sajalnarang on 14/3/17.
 */

public interface RetrofitInterface {
    @POST("/auth/user_login")
    Call<LoginResponse> login(@Query("email") String email, @Query("password") String password);
}
