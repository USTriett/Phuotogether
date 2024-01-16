package com.example.phuotogether.data_layer.auth;

import android.util.Log;
import android.widget.Toast;

import com.example.phuotogether.dto.User;
import com.example.phuotogether.service.RetrofitAPI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


import retrofit2.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


import com.example.phuotogether.service.RetrofitClient;


public class UserDatabase {

    public interface SignInCallback {
        void onSignInResult(boolean success, User user);
    }

    public void isSuccessSignIn(String email, String password, SignInCallback callback) {
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        try {
            JsonObject body = new JsonObject();
            body.addProperty("emailortel", email);
            body.addProperty("password", password);

            Call<List<UserResponse>> call = myApi.getUserByAccount(email, password);
            call.enqueue(new Callback<List<UserResponse>>() {
                @Override
                public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                    try {
                        if (response.isSuccessful()) {
                            UserResponse userResponse = response.body().get(0);
                            User user = new User(
                                    userResponse.getId(),
                                    userResponse.isLoginType(),
                                    userResponse.getEmailOrTel(),
                                    userResponse.getPassword(),
                                    userResponse.getFullName()
                            );
                            // callback to SignInManager
                            callback.onSignInResult(true, user);
                        } else {
                            callback.onSignInResult(false, new User(0, false, "", "", ""));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onSignInResult(false, new User(0, false, "", "", ""));
                    }
                }

                @Override
                public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                    Toast.makeText(null, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isSuccessSignIn(String email, String password) {
//        try{
//
//        }catch (Exception e){
//            Log.d("Error", "isSuccessSignIn: Lỗi " + e.toString());
//        }
//        return false;
        return true;
    }

    public boolean isSuccessForgotPassword(String email) {
        return false;
    }

}