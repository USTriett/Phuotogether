package com.example.phuotogether.data_layer.user;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.phuotogether.data_layer.auth.SignupRequestModel;
import com.example.phuotogether.data_layer.auth.UserResponse;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvatarUserRequestModel {
    private int id;
    File data;
    public AvatarUserRequestModel(int id, File data){
        this.data = data;
        this.id = id;
    }

    public void onUploadAvatar(){
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);

        try {
            Call<List<okhttp3.Response>> call = myApi.updateAvatar(String.valueOf(id), data);
            call.enqueue(new Callback<List<okhttp3.Response>>() {


                @Override
                public void onResponse(Call<List<okhttp3.Response>> call, Response<List<okhttp3.Response>> response) {

                }

                @Override
                public void onFailure(Call<List<okhttp3.Response>> call, Throwable t) {
                    Log.d("", "onFailure: updateFail" );
                    call.cancel();;
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
