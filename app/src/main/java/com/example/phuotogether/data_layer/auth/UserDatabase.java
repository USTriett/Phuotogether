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
    public interface SignUpCallback {
        void onSignUpResult(boolean success);
    }
    public void isSuccessSignUp(String emailortel, String password,String fullname, SignUpCallback callback) {
        Log.d("UserDatabase", "isSuccessSignUp: " + emailortel + password + fullname);
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        boolean loginType = emailOrTel(emailortel);
        try {
            Call<List<UserResponse>> call = myApi.insertUser(new SignupRequestModel(emailortel, loginType, password, fullname));
            call.enqueue(new Callback<List<UserResponse>>() {
                @Override
                public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                    if (!response.isSuccessful()) {
                        try {
                            Log.d("UserDatabase", "Error Body: " + response.errorBody().string());
                            callback.onSignUpResult(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Log.d("UserDatabase", "onResponse: " + response.body().get(0).getFullName());
                        callback.onSignUpResult(true);
                    }
                }

                @Override
                public void onFailure(Call<List<UserResponse>> call, Throwable t) {

                }

            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void isSuccessSignIn(String email, String password, SignInCallback callback) {
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        try {
            JsonObject body = new JsonObject();
            body.addProperty("emailortel", email);
            body.addProperty("password", password);

            Call<List<UserResponse>> call = myApi.getUserByAccount(new LoginRequestModel(email, password));
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
                    //Toast.makeText(null, "Error", Toast.LENGTH_SHORT).show();
                    callback.onSignInResult(false, new User());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSuccessForgotPassword(String email) {
//        return email.equals(VALID_EMAIL);
        return false;
    }

    private boolean emailOrTel(String emailOrTel) {
        return emailOrTel.contains("@");
    }

}
