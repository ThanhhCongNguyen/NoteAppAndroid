package com.example.noteappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.noteappandroid.R;
import com.example.noteappandroid.database.NoteDatabase;
import com.example.noteappandroid.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView backImage, doneButton;
    private EditText titleText, subTitleText, contentText;
    private TextView datetimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        initView();
        initData();
        backImage.setOnClickListener(this::onClick);
        doneButton.setOnClickListener(this::onClick);
    }

    private void initView() {
        backImage = findViewById(R.id.backImage);
        titleText = findViewById(R.id.titleNote);
        subTitleText = findViewById(R.id.subtitleNote);
        contentText = findViewById(R.id.contentNote);
        datetimeText = findViewById(R.id.dateTimeTextView);
        doneButton = findViewById(R.id.doneImage);
    }

    private void initData() {
        datetimeText.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );
    }

    private void saveNote() {
        if (titleText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note title can't be empty", Toast.LENGTH_LONG).show();
            return;
        } else if (subTitleText.getText().toString().trim().isEmpty()
                && contentText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note content can't be empty", Toast.LENGTH_LONG).show();
            return;
        }

        final Note note = new Note();
        note.setTitle(titleText.getText().toString().trim());
        note.setSubTitle(subTitleText.getText().toString().trim());
        note.setNoteText(contentText.getText().toString().trim());
        note.setDateTime(datetimeText.getText().toString().trim());

        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NoteDatabase.getNoteDatabase(getApplicationContext()).noteDao().insert(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        new SaveNoteTask().execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backImage:
                onBackPressed();
                break;
            case R.id.doneImage:
                saveNote();
                break;
        }
    }
}