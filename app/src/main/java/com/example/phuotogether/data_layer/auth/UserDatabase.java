package com.example.phuotogether.data_layer.auth;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserDatabase {
    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public boolean isSuccessSignIn(String email, String password) {
        try{

        }catch (Exception e){
            Log.d("Error", "isSuccessSignIn: Lỗi " + e.toString());
        }
        return false;
    }

    public boolean isSuccessForgotPassword(String email) {
//        return email.equals(VALID_EMAIL);
        return false;
    }

}
