package com.google.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomAdapter extends ArrayAdapter<String> {


    CustomAdapter(Context context,String[] users){
        super(context,R.layout.top_10_items,users);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater bulkinflater = LayoutInflater.from(getContext());
        View customView = bulkinflater.inflate(R.layout.top_10_items,parent,false);

        String singleUser = getItem(position);
        TextView strText = (TextView)customView.findViewById(R.id.str_view);
        ImageView imgView = (ImageView)customView.findViewById(R.id.avatar_view);
        strText.setText(singleUser);
        imgView.setImageResource(R.drawable.avatr);
        //@tools:sample/avatars
        return customView;
    }
}
