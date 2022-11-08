package com.example.noteappandroid.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.net.VpnManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.noteappandroid.R;
import com.example.noteappandroid.adapters.NotesAdapter;
import com.example.noteappandroid.database.NoteDatabase;
import com.example.noteappandroid.entities.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_NODE_ADD_CODE = 1;

    private FloatingActionButton createButton;
    private RecyclerView noteRecyclerView;

    private List<Note> noteList;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setUpOnClick();
        setUpRecyclerView();
        getNotes();
    }

    private void initView() {
        createButton = findViewById(R.id.createButton);
        noteRecyclerView = findViewById(R.id.recyclerView);
    }

    private void setUpRecyclerView() {
        noteRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList);
        noteRecyclerView.setAdapter(notesAdapter);
    }

    private void getNotes() {

        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NoteDatabase.getNoteDatabase(getApplicationContext()).noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if (notes.size() == 0) {
                    noteList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();
                } else {
                    if(noteList.size() != 0){
                        noteList.add(0, notes.get(0));
                        notesAdapter.notifyItemInserted(0);
                    }else {
                        noteList.addAll(notes);
                        notesAdapter.notifyDataSetChanged();
                    }
                }
                noteRecyclerView.smoothScrollToPosition(0);
            }
        }

        new GetNotesTask().execute();
    }

    private void setUpOnClick() {
        createButton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createButton:
                startActivityForResult(
                        new Intent(MainActivity.this, CreateNoteActivity.class),
                        REQUEST_NODE_ADD_CODE
                );
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NODE_ADD_CODE && resultCode == RESULT_OK) {
            getNotes();
        }
    }
}