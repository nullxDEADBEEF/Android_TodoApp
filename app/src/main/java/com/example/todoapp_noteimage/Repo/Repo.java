package com.example.todoapp_noteimage.Repo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.todoapp_noteimage.model.Note;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repo {

    private static final Repo repo = new Repo();
    private ArrayList<Note> noteList = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage fbStorage = FirebaseStorage.getInstance();
    private static final String NOTES = "notes";
    private static final String TITLE = "title";

    public static Repo r() {
        return repo;
    }

    public void uploadBitmap(Note note, Bitmap bitmap) {
        StorageReference storageRef = fbStorage.getReference(note.getId());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        storageRef.putBytes(outputStream.toByteArray()).addOnCompleteListener(snapshot -> {
            System.out.println("Upload went OK " + snapshot);
        }).addOnFailureListener(exception -> {
            System.out.println("Failed to upload " + exception);
        });

    }

    public void downloadBitmap(String filename) {
        StorageReference storageRef = fbStorage.getReference(filename);
        final int MAX_SIZE = 1024 * 1024;
        storageRef.getBytes(MAX_SIZE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            // update view
        }).addOnFailureListener(exception -> {
            System.out.println("Error in the download: " + exception);
        });
    }

    public void addNote(Note note) {
        DocumentReference docRef = db.collection(NOTES).document(note.getId());
        Map<String, String> docData = new HashMap<>();
        docData.put(TITLE, note.getTitle());
        docRef.set(docData).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                System.out.println("There was an error when we tried to save: " + task.getException());
            }
        });
        noteList.add(note);
    }

    public void updateNote(Note note) {
        // we can just write to the same id, since we assume
        // it already exists, therefore it will just overwrite
        // the previous title
        DocumentReference docRef = db.collection(NOTES).document(note.getId());
        Map<String, String> docData = new HashMap<>();
        docData.put(TITLE, note.getTitle());
        docRef.set(docData).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                System.out.println("There was an error when we tried to update: " + task.getException());
            }
        });
        note.setTitle(note.getTitle());
    }

    public void deleteNote(Note note) {
        DocumentReference docRef = db.collection(NOTES).document(note.getId());
        docRef.delete();
        noteList.remove(note);
        System.out.println("Note deleted!");
    }

    public ArrayList<Note> getNotes() { return noteList; }
    public Note getNote(int index) {
        return noteList.get(index);
    }
}
