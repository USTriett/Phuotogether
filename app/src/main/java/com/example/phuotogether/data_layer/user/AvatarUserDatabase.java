package com.example.phuotogether.data_layer.user;

import android.util.Log;

import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AvatarUserDatabase{
    public static void updateAvatar(String userId, File avatarFile) {
        // Create request body for user ID
        RequestBody userIdRequestBody = RequestBody.create(MediaType.parse("text/plain"), userId);

        // Create multipart request body for avatar file
        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), avatarFile);
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatar", avatarFile.getName(), fileRequestBody);
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);

        // Send the POST request
        Call<ResponseBody> call = myApi.updateAvatar(userIdRequestBody, avatarPart);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
            }
        });
    }


}

