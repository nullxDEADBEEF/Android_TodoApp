package com.example.todoapp_noteimage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todoapp_noteimage.model.Note;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private ArrayList<Note> data;
    private int image;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, ArrayList<Note> data, int image) {
        this.data = data;
        this.image = image;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.myrow, null);
        }

        TextView textView = convertView.findViewById(R.id.myTextView);
        textView.setText(data.get(position).getTitle());
        ImageView imageView = convertView.findViewById(R.id.myImageView);
        imageView.setImageResource(image);
        return convertView;
    }

    public ArrayList<Note> getData() {
        return data;
    }
}
