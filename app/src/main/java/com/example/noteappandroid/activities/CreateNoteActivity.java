package com.example.noteappandroid.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.noteappandroid.R;
import com.example.noteappandroid.database.NoteDatabase;
import com.example.noteappandroid.entities.Note;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView backImage, doneButton;
    private EditText titleText, subTitleText, contentText;
    private TextView datetimeText;
    private View viewSubTitleIndicator;
    private ImageView imageNote;

    private LinearLayout bottomSheetLayout;
    private BottomSheetBehavior sheetBehavior;

    private String selectedNoteColor = "#FFFFFFFF";
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        initView();
        initData();
        setUpOnClick();
        setSubTitleIndicator();
    }

    private void initView() {
        backImage = findViewById(R.id.backImage);
        titleText = findViewById(R.id.titleNote);
        subTitleText = findViewById(R.id.subtitleNote);
        contentText = findViewById(R.id.contentNote);
        datetimeText = findViewById(R.id.dateTimeTextView);
        doneButton = findViewById(R.id.doneImage);
        bottomSheetLayout = findViewById(R.id.llMiscellaneous);
        sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        viewSubTitleIndicator = findViewById(R.id.indicatorView);
        imageNote = findViewById(R.id.imageView);
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
        note.setBackgroundColor(selectedNoteColor);

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

    private void setSubTitleIndicator() {
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubTitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }

    private void setUpOnClick() {
        backImage.setOnClickListener(this::onClick);
        doneButton.setOnClickListener(this::onClick);
        bottomSheetLayout.setOnClickListener(this::onClick);

    }

    private void initMiscellaneous() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final ImageView imageColor1 = bottomSheetLayout.findViewById(R.id.imageColor1);
        final ImageView imageColor2 = bottomSheetLayout.findViewById(R.id.imageColor2);
        final ImageView imageColor3 = bottomSheetLayout.findViewById(R.id.imageColor3);
        final ImageView imageColor4 = bottomSheetLayout.findViewById(R.id.imageColor4);
        final ImageView imageColor5 = bottomSheetLayout.findViewById(R.id.imageColor5);

        bottomSheetLayout.findViewById(R.id.viewColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#323232";
                imageColor1.setImageResource(R.drawable.ic_done);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubTitleIndicator();
            }
        });

        bottomSheetLayout.findViewById(R.id.viewColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#F2E34C";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.ic_done);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubTitleIndicator();
            }
        });

        bottomSheetLayout.findViewById(R.id.viewColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#D10000";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.ic_done);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubTitleIndicator();
            }
        });

        bottomSheetLayout.findViewById(R.id.viewColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#2E2EFF";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.ic_done);
                imageColor5.setImageResource(0);
                setSubTitleIndicator();
            }
        });

        bottomSheetLayout.findViewById(R.id.viewColor5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#00A300";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(R.drawable.ic_done);
                setSubTitleIndicator();
            }
        });

        bottomSheetLayout.findViewById(R.id.llAddImage).setOnClickListener(view -> {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        CreateNoteActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_STORAGE_PERMISSION
                );
                Log.d("thanh", "1");
            } else {
                Log.d("thanh", "2");
                selectImage();
            }
        });

    }

    private void selectImage() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, REQUEST_CODE_SELECT_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("thanh", data.getDataString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG);
            }
        }
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
            case R.id.llMiscellaneous:
                initMiscellaneous();
                break;
        }
    }
}