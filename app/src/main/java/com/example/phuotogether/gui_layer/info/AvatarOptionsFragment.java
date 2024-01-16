package com.example.phuotogether.gui_layer.info;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.phuotogether.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvatarOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvatarOptionsFragment extends DialogFragment {
    public AvatarOptionsFragment() {
        // Required empty public constructor
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

        View view = inflater.inflate(R.layout.fragment_avatar_options, container, false);
//        listView = new AvatarOptionsListView(getContext());
//        view.findViewById(R.id.avatarOptionListView).
        // Apply the float-up animation to the root view of the fragment
        Animator animator = AnimatorInflater.loadAnimator(getContext(), R.animator.float_up);
        animator.setTarget(view);
        animator.start();

        return view;
    }
}