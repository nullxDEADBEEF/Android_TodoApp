package com.example.todoapp_noteimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todoapp_noteimage.Repo.Repo;
import com.example.todoapp_noteimage.model.Note;

public class MainActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private EditText taskText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repo.r().addNote(new Note("Implement The UI"));
        Repo.r().addNote(new Note("Add new items to list"));
        Repo.r().addNote(new Note("Save text changes"));
        ListView listView = findViewById(R.id.myListView);
        taskText = findViewById(R.id.myEditText);
        adapter = new MyAdapter(this, Repo.r().getNotes(), R.drawable.papertray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((_listView, linearLayout, adapterPos, arrayPos) -> {
            System.out.println("Click on row: " + arrayPos);
            Intent intent = new Intent(this, DetailActivity.class);
            Note txt = Repo.r().getNote((int)arrayPos);
            intent.putExtra("data", txt.getTitle());
            intent.putExtra("arrayPos", (int)arrayPos);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    public void addTask(View view) {
        Note task = new Note(String.valueOf(taskText.getText()));
        Repo.r().addNote(task);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, task.getTitle() + " Added", Toast.LENGTH_LONG).show();
        taskText.setText("");
        System.out.println("added task!");
    }
}