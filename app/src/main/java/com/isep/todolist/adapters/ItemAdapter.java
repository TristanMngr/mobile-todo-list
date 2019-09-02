package com.isep.todolist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.isep.todolist.R;
import com.isep.todolist.models.Item;
import com.isep.todolist.models.Todo;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private Context mContext;
    private List<Item> itemList = new ArrayList<>();

    public ItemAdapter(@NonNull Context context, @LayoutRes ArrayList<Item> list) {
        super(context, 0, list);
        mContext = context;
        itemList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        Item currentItem = itemList.get(position);

        TextView itemListTitle = listItem.findViewById(R.id.item_title);
        itemListTitle.setText(currentItem.getName());
        itemListTitle.setTag(currentItem.getId());

        return listItem;
    }
}
