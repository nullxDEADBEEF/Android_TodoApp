package com.example.todoapp_noteimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp_noteimage.Repo.Repo;
import com.example.todoapp_noteimage.model.Note;

import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {

    EditText myDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        myDetailTextView = findViewById(R.id.myDetailEditText);
        String txt = getIntent().getStringExtra("data");
        myDetailTextView.setText(txt);
    }

    public void returnToListView(View view) {
        finish();
    }

    public void cameraButtonPressed(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }

    public void galleryButtonPressed(View view) {
        // implicit intent, allows user to choose among different services to accomplish this task
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    public void deleteNote(View view) {
        int arrayPos = getIntent().getIntExtra("arrayPos", 0);
        Note note = Repo.r().getNote(arrayPos);
        Repo.r().deleteNote(note);
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_LONG).show();
        finish();
    }

    public void saveTextChanges(View view) {
        int arrayPos = getIntent().getIntExtra("arrayPos", 0);
        Note note = Repo.r().getNote(arrayPos);
        note.setTitle(myDetailTextView.getText().toString());
        Repo.r().updateNote(note);
        Toast.makeText(this, "Changes Saved", Toast.LENGTH_LONG).show();
        System.out.println("Text saved!");
    }
}