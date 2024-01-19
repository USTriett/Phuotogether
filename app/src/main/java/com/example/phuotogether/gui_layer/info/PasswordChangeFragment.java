package com.example.phuotogether.gui_layer.info;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.phuotogether.R;
import com.example.phuotogether.data_layer.auth.UserDatabase;
import com.example.phuotogether.data_layer.auth.UserResponse;
import com.example.phuotogether.data_layer.user.UserUpdateRequest;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordChangeFragment extends DialogFragment {
    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private Button changeBtn;
    private View viewHold;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Tạo một Dialog mới
        Dialog dialog = new Dialog(requireContext());

        // Thiết lập layout cho Dialog
        dialog.setContentView(R.layout.fragment_change_password);

        // Thiết lập kích thước cho Dialog
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // Thiết lập chiều rộng và chiều cao của Dialog
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;  // Kích thước chiều rộng có thể được thay đổi theo yêu cầu
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;  // Kích thước chiều cao có thể được thay đổi theo yêu cầu

        // Thiết lập các thuộc tính khác cho Dialog (nếu cần)
        // ...

        // Áp dụng các thay đổi về kích thước vào Dialog
        dialog.getWindow().setAttributes(layoutParams);

        return dialog;
    }
    public PasswordChangeFragment(){}
    public static PasswordChangeFragment newInstance() {
        return new PasswordChangeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewHold = inflater.inflate(R.layout.fragment_change_password, container, false);

        oldPassword = viewHold.findViewById(R.id.editTextCurrentPassword);
        newPassword = viewHold.findViewById(R.id.editTextNewPassword);
        confirmPassword = viewHold.findViewById(R.id.editTextConfirmPassword);
        changeBtn = viewHold.findViewById(R.id.buttonChangePassword);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPasswordText = oldPassword.getText().toString();
                String newPasswordText = newPassword.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();
                viewHold.findViewById(R.id.WrongPassword).setVisibility(View.GONE);
                viewHold.findViewById(R.id.WrongConfirmPassword).setVisibility(View.GONE);
                // Kiểm tra xem các trường dữ liệu có hợp lệ không
                if(User.getInstance().getPassword().compareTo(oldPasswordText) != 0){
                    viewHold.findViewById(R.id.WrongPassword).setVisibility(View.VISIBLE);

                }else{
                    if(newPasswordText.compareTo(confirmPasswordText) != 0 && newPasswordText.length() < 8){
                        viewHold.findViewById(R.id.WrongConfirmPassword).setVisibility(View.VISIBLE);
                    }
                    else {
                        performChangePassword(oldPasswordText, newPasswordText);

                    }
                }


            }
        });
        return viewHold;
    }

    private void performChangePassword(String oldPasswordText, String newPasswordText) {
        UserUpdateRequest request = new UserUpdateRequest(User.getInstance().getId(), newPasswordText, "John Doe");
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);

        // Make the API call
        Call<UserResponse> call = myApi.updateUser(request);
        call.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();

                if(response.isSuccessful()){
                    User.getInstance().updateInfo(
                            User.getInstance().getId(),
                            false,
                            User.getInstance().getEmail(),
                            newPasswordText,
                            User.getInstance().getFullName()
                    );
                    ((MainActivity) getActivity()).updateFragments();
                    dismissNow();
                    Toast.makeText(getActivity(), "Có lỗi xảy ra", Toast.LENGTH_LONG);
                }


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Có lỗi xảy ra", Toast.LENGTH_LONG);
                dismissNow();
            }
        });
    }

}
