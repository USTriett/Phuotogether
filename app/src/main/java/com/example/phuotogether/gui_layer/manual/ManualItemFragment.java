package com.example.phuotogether.gui_layer.manual;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.phuotogether.R;

import org.jetbrains.annotations.Contract;

public class ManualItemFragment extends Fragment {
    int mPosition;
    String mManualContent;
    public ManualItemFragment() {
        // Required empty public constructor
    }

    public ManualItemFragment(int position, String manualContent){
        // query để lấy dữ liệu có id = position từ database
        // giờ giả bộ gán cứng dữ liệu
        mPosition = position;
        mManualContent = manualContent;
    }

    @NonNull
    public static ManualItemFragment newInstance(int position, String manualContent) {
        return new ManualItemFragment(position, manualContent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_manual_item, container, false);
        TextView fragmentManualItemContentView = rootView.findViewById(R.id.fragmentManualItemContentView);
        fragmentManualItemContentView.setText(Html.fromHtml(mManualContent));
        //fragmentManualItemContentView.loadDataWithBaseURL("file:///android_asset/", mManualContent, "text/html", "UTF-8", null);
        fragmentManualItemContentView.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }
}