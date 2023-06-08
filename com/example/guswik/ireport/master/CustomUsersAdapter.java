package com.example.guswik.ireport.master;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.guswik.ireport.R;

public class CustomUsersAdapter extends ArrayAdapter<DetailInvestasiItem> {
    public CustomUsersAdapter(Context context, ArrayList<DetailInvestasiItem> detailInvestasiItems) {
        super(context, 0, detailInvestasiItems);
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.listviewcolom, parent, false);
        }

         // Get the data item for this position
         DetailInvestasiItem detailInvestasiItem = getItem(position);

         // Lookup view for data population
        TextView tvRBA = (TextView) convertView.findViewById(R.id.textViewRBA);
        TextView tvVolume = (TextView) convertView.findViewById(R.id.textViewVolume);
        TextView tvHarga = (TextView) convertView.findViewById(R.id.textViewHarga);
        TextView tvTotal = (TextView) convertView.findViewById(R.id.textViewTotalHarga);
        // Populate the data into the template view using the data object
        tvRBA.setText(detailInvestasiItem.getRBA());
        tvVolume.setText(detailInvestasiItem.getVolume());
        tvHarga.setText(detailInvestasiItem.getHarga());
        tvTotal.setText(detailInvestasiItem.getTotal());
        // Return the completed view to render on screen
        return convertView;
    }
}
