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
    private String id;
    File avatar;

    public AvatarUserRequestModel(int id, File data) {
        this.avatar = data;
        this.id = String.valueOf(id);
    }

}
