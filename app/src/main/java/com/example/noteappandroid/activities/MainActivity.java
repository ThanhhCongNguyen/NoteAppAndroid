package com.example.noteappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.noteappandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_NODE_ADD_CODE = 1;
    private FloatingActionButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        createButton.setOnClickListener(this::onClick);
    }

    private void initView() {
        createButton = findViewById(R.id.createButton);
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
}