package com.example.phuotogether.gui_layer.manual;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phuotogether.R;

import org.jetbrains.annotations.Contract;

public class ManualItemFragment extends Fragment {
    int mPosition;
    public ManualItemFragment() {
        // Required empty public constructor
    }

    public ManualItemFragment(int position){
        // query để lấy dữ liệu có id = position từ database
        // giờ giả bộ gán cứng dữ liệu
        mPosition = position;
    }

    @NonNull
    public static ManualItemFragment newInstance(int position) {
        return new ManualItemFragment(position);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_manual_item, container, false);
        TextView textViewTitle = rootView.findViewById(R.id.manualItemTitle);
        TextView textViewContent = rootView.findViewById(R.id.manualItemContent);
        textViewTitle.setText("All Too Well (10 Minute Version) (Taylor’s Version) [From The Vault]");
        textViewContent.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum consequat sem nulla, ut pretium diam condimentum vitae. Maecenas malesuada elit sit amet tellus rhoncus, in vestibulum quam viverra. Curabitur vitae dapibus ex. Vestibulum sodales nisi nisi. Praesent justo dolor, viverra vitae pretium nec, dapibus nec odio. Vestibulum ultricies pretium ante, et ullamcorper lacus porttitor at. Aliquam eu tristique purus, vel hendrerit lorem. Etiam sed arcu urna.\n\nPraesent efficitur vel lorem sed sodales. Nullam rutrum ullamcorper mauris, dictum tincidunt augue rhoncus imperdiet. In nec feugiat urna. Duis a turpis ante. Suspendisse lobortis lacus sed neque sodales condimentum vel congue tellus. Proin ultricies neque ligula, porttitor lobortis nisi commodo ac. Quisque eget rutrum nisi, in ultrices lorem. Ut non ultrices ante. In et ante elit. Maecenas ac porttitor odio. Nullam sit amet lacinia mauris. Cras eget lacus sit amet velit tristique sagittis nec quis lectus. Aenean rhoncus tempor nibh, in mollis risus euismod nec. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n\nPhasellus molestie metus vitae efficitur sagittis. Aliquam non porttitor purus. Suspendisse mi nisi, egestas quis mauris viverra, tempus viverra ex. Nunc varius dapibus volutpat. Proin hendrerit mi erat, nec convallis eros imperdiet id. Mauris sed dolor ante. Duis eget condimentum massa. Sed nibh sem, euismod ac felis sed, rutrum ultricies arcu. Curabitur quis libero at lorem ultrices vehicula in non lacus. Praesent vitae dui volutpat, vehicula mi in, rutrum nibh. Nullam sit amet enim consectetur ex consectetur vulputate vel eget arcu. Cras malesuada, lectus eget molestie mattis, augue dolor feugiat turpis, quis auctor dui nibh eu urna.\n\nDonec accumsan, erat et placerat fringilla, nunc odio blandit velit, quis eleifend metus erat sed nunc. Donec efficitur volutpat sodales. Vivamus sollicitudin sollicitudin scelerisque. Vivamus tempus laoreet venenatis. Fusce eleifend metus a nisi commodo euismod. Fusce eget tristique mi. Vivamus leo nisl, pretium vel eleifend et, efficitur at risus. Suspendisse id venenatis nisi, sollicitudin interdum metus.\n\nPellentesque in elit ullamcorper nunc eleifend tincidunt. Phasellus laoreet augue a metus fermentum, in consequat magna vestibulum. Aliquam nec nulla a ex euismod porta. Duis dolor lacus, vehicula ac tortor id, ultricies consectetur velit. Vestibulum mattis nunc sed lacus sodales porta. Nam elementum lorem id dolor blandit, quis venenatis ipsum lobortis. Morbi dictum et tortor sed consectetur. Proin posuere convallis rhoncus. Duis ipsum elit, viverra a tincidunt eget, rutrum a libero. Duis sed pretium lacus, eu aliquam eros. Nam at leo a nibh posuere aliquam eu egestas dolor. Quisque ac tellus tincidunt, pulvinar dui quis, maximus diam.");
        textViewContent.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }
}