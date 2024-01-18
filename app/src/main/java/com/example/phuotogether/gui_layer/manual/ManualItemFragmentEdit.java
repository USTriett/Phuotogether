package com.example.phuotogether.gui_layer.manual;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.manual.ManualManager;
import com.example.phuotogether.gui_layer.auth.SignUp.SignUpFragment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ManualItemFragmentEdit extends Fragment {
    int mPosition;
    String mManualContent;
    String mManualTitle;
    View mRootView;
    EditText mFragmentManualItemEditTitleView;
    EditText mFragmentManualItemEditContentView;
    TextView mFragmentManualItemEditSaveView;
    TextView mFragmentManualItemEditCancelView;

    public ManualItemFragmentEdit() {
        // Required empty public constructor
    }

    public ManualItemFragmentEdit(int position, String manualTitle, String manualContent){
        // query để lấy dữ liệu có id = position từ database
        // giờ giả bộ gán cứng dữ liệu
        mPosition = position;
        mManualTitle = manualTitle;
        mManualContent = manualContent;
    }

    @NonNull
    public static ManualItemFragmentEdit newInstance(int position, String manualTitle, String manualContent) {
        return new ManualItemFragmentEdit(position, manualTitle, manualContent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_manual_item_edit, container, false);

        // Custom EditText for title
        mFragmentManualItemEditTitleView = mRootView.findViewById(R.id.fragmentManualItemEditTitleView);
        mFragmentManualItemEditTitleView.setText(mManualTitle);
        mFragmentManualItemEditTitleView.setMovementMethod(new ScrollingMovementMethod());
        setCustomSelection(mFragmentManualItemEditTitleView);

        // Custom EditText for content
        mFragmentManualItemEditContentView = mRootView.findViewById(R.id.fragmentManualItemEditContentView);
        mFragmentManualItemEditContentView.setText(mManualContent);
        mFragmentManualItemEditContentView.setMovementMethod(new ScrollingMovementMethod());
        setCustomSelection(mFragmentManualItemEditContentView);

        // Custom Save
        this.setOnClickListenerSave(mRootView);

        // Custom Cancel
        this.setOnClickListenerCancel(mRootView);

        // Set on text changed for title
        setOnTextChanged(mFragmentManualItemEditTitleView);

        return mRootView;
    }

    private void setOnClickListenerSave(View rootView){
        mFragmentManualItemEditSaveView = rootView.findViewById(R.id.fragmentManualItemEditSaveView);
        mFragmentManualItemEditSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuẩn bị dữ liệu
                mManualTitle = mFragmentManualItemEditTitleView.getText().toString();
                mManualContent = mFragmentManualItemEditContentView.getText().toString();

                // Tạo file mới -> tí nữa thêm trường hợp sửa file cũ
                String userId = String.valueOf(ManualManager.user.getId());
                File userFolder = new File(ManualManager.context.getFilesDir(), userId);
                String fileName = "manual" + mPosition + ".xml";
                File manualFile = new File(userFolder, fileName);
                try {
                    FileWriter writer = new FileWriter(manualFile);
                    writer.write(toHtmlFileContent());
                    Log.d("file content", toHtmlFileContent());
                    writer.close();
                } catch (IOException e) {
                    Log.e("cannot write to file", "");
                    e.printStackTrace();
                }

                // Fetch dữ liệu các file
                ManualManager.fetchManualList();

                // Xóa fragment edit hiện tại
                requireActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
            }
        });
    }

    private void setOnClickListenerCancel(View rootView){
        mFragmentManualItemEditCancelView = rootView.findViewById(R.id.fragmentManualItemEditCancelView);
        mFragmentManualItemEditCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentActivity) mRootView.getContext())
                        .getSupportFragmentManager()
                        .popBackStack();
            }
        });
    }

    private void setCustomSelection(EditText editText) {
        // Set the long click listener to start the text selection
        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.text_selection_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Do something if needed
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.menu_copy){
                    copyText(editText);
                    mode.finish(); // Finish the action mode
                    return true;
                }
                else
                    return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Do something if needed when action mode is destroyed
            }
        });
    }
    private void copyText(EditText editText) {
        // Get the selected text
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        CharSequence selectedText = editText.getText().subSequence(start, end);

        // Copy the text to the clipboard
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", selectedText);
        clipboard.setPrimaryClip(clip);
    }

    private String toHtmlFileContent(){

        // Split the plain content into lines
        String[] lines = mManualContent.split("\\n");

        // Wrap each line in <p> tags
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<h1>").append(mManualTitle).append("</h1>");
        for (String line : lines) {
            htmlStringBuilder.append("<p>").append(line).append("</p>");
        }

        return htmlStringBuilder.toString();
    }

    private void setOnTextChanged(EditText editText){
        Log.d("crashing down", "");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
                    mFragmentManualItemEditSaveView.setVisibility(View.GONE);
                }
                else{
                    mFragmentManualItemEditSaveView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
}