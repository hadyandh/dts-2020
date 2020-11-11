package com.hunhun.sqlliteapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hunhun.sqlliteapp.R;
import com.hunhun.sqlliteapp.model.Data;

import java.util.List;

public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> items;

    public Adapter(Activity activity, List<Data> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.list_row, null);

        TextView id = view.findViewById(R.id.id);
        TextView name = view.findViewById(R.id.name);
        TextView address = view.findViewById(R.id.address);

        Data data = items.get(position);

        id.setText(data.getId());
        name.setText(data.getName());
        address.setText(data.getAddress());

        return view;
    }
}