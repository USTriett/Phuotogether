package com.example.phuotogether.gui_layer.info;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phuotogether.R;

import java.util.ArrayList;

public class AvatarOptionsListView extends ListView {
    public AvatarOptionsListView(Context context, AttributeSet set){
        super(context, set);
        initItem();
//        this.adapter = new ArrayAdapter<>(context, R.id.avatarOptionListView, this.items);
        this.adapter = new AvatarOptionsAdapter(context, this.items);
        this.setAdapter(this.adapter);
    }
    public AvatarOptionsListView(Context context) {
        super(context);
        initItem();
//        this.adapter = new ArrayAdapter<>(context, R.id.avatarOptionListView, this.items);
        this.adapter = new AvatarOptionsAdapter(context, this.items);
        this.setAdapter(this.adapter);
    }
    class Item{
        String optionName;
        Drawable drawableIcon;

        public Item(String name, Drawable drawableIcon){
            this.drawableIcon=drawableIcon;
            this.optionName = name;
        }
    }
    class AvatarOptionsAdapter extends BaseAdapter{
        private ArrayList<Item> items;
        private final Context context;

        public AvatarOptionsAdapter(Context context, ArrayList<Item> items){
            this.context = context;
            this.items = items;
        }
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return this.items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.avatar_option_view, parent, false);

                viewHolder = new ViewHolder();
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.optionView = convertView.findViewById(R.id.avatarOptionTextView);

            viewHolder.optionView.setText(items.get(position).optionName);
            viewHolder
                    .optionView
                    .setCompoundDrawablesRelativeWithIntrinsicBounds(items.get(position).drawableIcon,
                            null, null, null);
            return convertView;
        }

        private class ViewHolder {
            TextView optionView;

        }
    }
    private ArrayList<Item> items = new ArrayList<>();
    private AvatarOptionsAdapter adapter;

    private void initItem(){
        items.add(new Item("Change Avatar", getResources().getDrawable(R.drawable.heart_svgrepo_com)));
        items.add(new Item("View Avatar", getResources().getDrawable(R.drawable.heart_svgrepo_com)));
    }
}
