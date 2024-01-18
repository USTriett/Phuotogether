package com.example.phuotogether.data_layer.auth;

import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserDatabase {
    MediaType mediaType = MediaType.parse("application/json");

    OkHttpClient client = new OkHttpClient();

    String post(String url, JSONObject json) throws IOException {
        String email = "";
        String password = "";

        try {
            email = json.getString("emailortel");
            password = json.getString("password");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody formBody = new FormBody.Builder()
                .add("emailortel",email)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Log.d("request", "post: " + request.toString());
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    public interface SignInCallback {
        void onSignInResult(boolean success, String response);
    }
    public void isSuccessSignIn(String email, String password, final SignInCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailortel", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String json =  jsonObject.toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Thực hiện hoạt động mạng ở đây (ví dụ: gửi yêu cầu HTTP)
                    String response = post("http://10.0.2.2:5000/user/get_user_by_account", jsonObject);

                    // Gửi kết quả về trong callback
                    callback.onSignInResult(true, response);
                } catch (Exception e) {
                    // Xử lý lỗi và gửi kết quả về trong callback
                    callback.onSignInResult(false, e.toString());
                }
            }
        }).start();


    }

    public boolean isSuccessForgotPassword(String email) {
//        return email.equals(VALID_EMAIL);
        return false;
    }

}
