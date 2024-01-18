package com.example.phuotogether.gui_layer.info;

import static android.app.Activity.RESULT_OK;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.info.ImageProcessor;
import com.example.phuotogether.data_layer.user.AvatarUserRequestModel;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;
//import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvatarOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvatarOptionsFragment extends DialogFragment {

    private View view;
    private ImageView avatar;
    private Uri selectedImageUri;
    private DialogInterface dialog;

    public AvatarOptionsFragment() {
        // Required empty public constructor
    }
    public AvatarOptionsFragment(ImageView avatar, Uri holder){
        this.avatar = avatar;
        this.selectedImageUri = holder;
    }


    // TODO: Rename and change types and number of parameters
    public static AvatarOptionsFragment newInstance() {
        return new AvatarOptionsFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // Set the desired position of the dialog
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM; // Change to your desired gravity
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        changeOpacityActivity(0.5f);
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
//        if (selectedImageUri != null) {
//
//            .with(getActivity())
//                    .load(selectedImageUri)
//                    .into(avatar);
//        }
        super.onDismiss(dialog);

        changeOpacityActivity(1);


    }

    private void changeOpacityActivity(float alpha){
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = alpha; // Change the value between 0.0f to 1.0f to adjust opacity
        window.setAttributes(params);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_avatar_options, container, false);
        AvatarOptionsListView listView = view.findViewById(R.id.avatarOptionListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                if (position == 0) {
                    openDeviceGallery();
                }
            }

        });
        // Apply the float-up animation to the root view of the fragment
        Animator animator = AnimatorInflater.loadAnimator(getContext(), R.animator.float_up);
        animator.setTarget(view);
        animator.start();

        return view;
    }
    private static final int GALLERY_REQUEST_CODE = 1;

    private void openDeviceGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // The user has selected an image from the gallery
//            selectedImageUri = data.getData();
//            if (selectedImageUri != null) {
//
//                Picasso.
//                        with(getContext())
//                        .load(selectedImageUri)
//                        .into(avatar);
//                File f = uriToFile(selectedImageUri);
//                AvatarUserRequestModel model = new AvatarUserRequestModel(User.getInstance().getId(), f);
//                model.onUploadAvatar();
//            }

        }

    }
    public File uriToFile(Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        // Assuming you're in an Activity or a Fragment
        ContentResolver contentResolver = getContext().getContentResolver();

        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }

        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }


}

