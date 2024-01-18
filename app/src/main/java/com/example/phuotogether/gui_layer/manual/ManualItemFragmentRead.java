package com.example.phuotogether.gui_layer.manual;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phuotogether.R;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManualItemFragmentRead extends Fragment {
    public static final int TAB_POSITION = 2;

    int mPosition;
    String mManualContent;
    View mRootView;
    public ManualItemFragmentRead() {
        // Required empty public constructor
    }

    public ManualItemFragmentRead(int position, String manualContent){
        // query để lấy dữ liệu có id = position từ database
        // giờ giả bộ gán cứng dữ liệu
        mPosition = position;
        mManualContent = manualContent;
    }

    @NonNull
    public static ManualItemFragmentRead newInstance(int position, String manualContent) {
        return new ManualItemFragmentRead(position, manualContent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_manual_item_read, container, false);

        // Display content
        TextView fragmentManualItemReadContentView = mRootView.findViewById(R.id.fragmentManualItemReadContentView);
        fragmentManualItemReadContentView.setText(Html.fromHtml(mManualContent));
        //fragmentManualItemContentView.loadDataWithBaseURL("file:///android_asset/", mManualContent, "text/html", "UTF-8", null);
        fragmentManualItemReadContentView.setMovementMethod(new ScrollingMovementMethod());

        // Set on click for "Chỉnh sửa"
        setOnClickListenerEdit();

        // Set on click for "Trở về"
        setOnClickListenerCancel();

        return mRootView;
    }

    private void setOnClickListenerCancel(){
        TextView fragmentManualItemReadCancelView = mRootView.findViewById(R.id.fragmentManualItemReadCancelView);
        fragmentManualItemReadCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentActivity) mRootView.getContext())
                        .getSupportFragmentManager()
                        .popBackStack();
            }
        });
    }

    private void setOnClickListenerEdit(){
        TextView fragmentManualItemReadEditView = mRootView.findViewById(R.id.fragmentManualItemReadEditView);
        fragmentManualItemReadEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String convertResult[] = convertHTML(mManualContent);
                String title = convertResult[0];
                String content = convertResult[1];

                ManualItemFragmentEdit manualItemFragmentEdit = new ManualItemFragmentEdit(mPosition, title, content);
                addFragment(manualItemFragmentEdit, TAB_POSITION);

                FragmentTransaction transaction = ((FragmentActivity) mRootView.getContext())
                        .getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.manual, manualItemFragmentEdit);
                transaction.addToBackStack(null);                           // Add to back stack if needed
                transaction.commit();
            }
        });
    }

    public static String[] convertHTML(String mManualContent) {
        // Extract title
        Pattern titlePattern = Pattern.compile("<h1>(.*?)</h1>");
        Matcher titleMatcher = titlePattern.matcher(mManualContent);
        String title = "";
        if (titleMatcher.find()) {
            title = titleMatcher.group(1);
        }

        // Extract content
        Pattern contentPattern = Pattern.compile("<p>(.*?)</p>");
        Matcher contentMatcher = contentPattern.matcher(mManualContent);
        StringBuilder contentBuilder = new StringBuilder();
        while (contentMatcher.find()) {
            contentBuilder.append(contentMatcher.group(1)).append("\n");
        }
        String content = contentBuilder.toString().trim();

        // Build the result
        String result[] = {title, content};
        return result;
    }

    public void addFragment(Fragment fragment, int tabPosition) {
        MainFragmentPagerAdapter pagerAdapter = ((MainActivity) getContext()).getPagerAdapter();
        pagerAdapter.updateFragment(fragment, tabPosition);
    }
}