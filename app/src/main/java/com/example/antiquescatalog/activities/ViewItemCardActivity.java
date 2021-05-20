package com.example.antiquescatalog.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.antiquescatalog.R;

public class ViewItemCardActivity extends AppCompatActivity {
    private TextView tv_title,tv_condition,tv_category,tv_time_period,tv_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_card);
        setupToolBar();
        setupFields();

    }

    private void setupFields() {
        tv_title=findViewById(R.id.tv_title);
        tv_category=findViewById(R.id.tv_category);
        tv_condition=findViewById(R.id.tv_condition);
        tv_time_period=findViewById(R.id.tv_time_period);
        tv_note = findViewById(R.id.tv_note);

        tv_title.setText(getIntent().getStringExtra("title"));
        tv_category.setText(getIntent().getStringExtra("category"));
        tv_condition.setText(getIntent().getStringExtra("condition"));
        tv_time_period.setText(getIntent().getStringExtra("timePeriod"));
        tv_note.setText(getIntent().getStringExtra("note"));
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}