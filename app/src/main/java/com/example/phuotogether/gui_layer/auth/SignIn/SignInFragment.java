package com.example.phuotogether.gui_layer.auth.SignIn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.auth.SignIn.SignInManager;
import com.example.phuotogether.data_layer.auth.UserDatabase;


public class SignInFragment extends Fragment {
    private EditText etEmail, etPassword;
    private AppCompatButton btnSignIn, btnSignup;
    private ImageButton btnSignInWithGG, btnSignInWithFB, btnBack;
    private TextView tvForgotPassword, tvEmptyEmail, tvEmptyPassword, tvWrongEmail, tvWrongPassword;
    private SignInManager signInManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signin, container, false);

        signInManager = new SignInManager(new UserDatabase());

        setAndGetAllView(rootView);
        setEventClickSignInButton();
        setEventClickSignUpButton();
//        setEventClickForgotPasswordTextview();

        return rootView;
    }

//    private void setEventClickForgotPasswordTextview() {
//        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), ForgotPasswordActivity.class));
//            }
//        });
//    }

    private void setEventClickSignUpButton() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), SignUpActivity.class));
            }
        });
    }

    private void setEventClickSignInButton() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = etEmail.getText().toString();
                String passwordString = etPassword.getText().toString();

                if (signInManager.isSuccessSignIn(emailString, passwordString)) {
                    setAllNotificationOff();
                    showSuccessToast();
                    SignIn();
//                    startActivity(new Intent(getActivity(), TripListActivity.class));
                } else {
                    signInManager.handleSignInError(emailString, passwordString, tvEmptyEmail, tvEmptyPassword, tvWrongEmail, tvWrongPassword);
                }
            }
        });
    }

    private void SignIn() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FrameLayout frameLayout = getActivity().findViewById(R.id.signin_container);
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(manager.findFragmentById(R.id.signin_container))
                .commit();
        frameLayout.setVisibility(View.GONE);
    }

    private void setAllNotificationOff() {
        tvEmptyEmail.setVisibility(View.GONE);
        tvWrongEmail.setVisibility(View.GONE);
        tvEmptyPassword.setVisibility(View.GONE);
        tvWrongPassword.setVisibility(View.GONE);
    }

    private void showSuccessToast() {
        Context context = requireContext();
        CharSequence text = "Đăng nhập thành công!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void setAndGetAllView(View rootView) {
        etEmail = rootView.findViewById(R.id.editTextEmail);
        etPassword = rootView.findViewById(R.id.editTextPassword);
        tvForgotPassword = rootView.findViewById(R.id.textViewForgotPassword);
        btnSignIn = rootView.findViewById(R.id.buttonSignIn);
        btnSignup = rootView.findViewById(R.id.buttonSignUp);
        btnSignInWithFB = rootView.findViewById(R.id.buttonSignInFacebook);
        btnSignInWithGG = rootView.findViewById(R.id.buttonSignInGoogle);
        btnBack = rootView.findViewById(R.id.buttonBack);
        tvEmptyEmail = rootView.findViewById(R.id.tvEmptyEmailNotification);
        tvWrongEmail = rootView.findViewById(R.id.tvWrongEmailNotification);
        tvEmptyPassword = rootView.findViewById(R.id.tvEmptyPasswordNotification);
        tvWrongPassword = rootView.findViewById(R.id.tvWrongPasswordNotification);
    }
}