package com.example.antiquescatalog.activities;

import android.os.Bundle;

import com.example.antiquescatalog.models.Catalog;
import com.example.antiquescatalog.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.antiquescatalog.R;

public class AddNewItemActivity extends AppCompatActivity {

    private Catalog mCatalog;
    private EditText et_title;
    private RadioGroup rg_category, rg_condition, rg_time_period;
    private RadioButton rb_category, rb_condition, rb_time_period;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        setupToolBar();
        setupFAB();
        setupFields();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handelClick();
            }
        });
    }

    private void handelClick() {
        getCheckedRadioButtons();
        MainActivity.newItem=new Item(et_title.getText().toString(),rb_category.getText().toString(),
                rb_time_period.getText().toString(),rb_condition.getText().toString());
        finish();
    }


    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() !=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void setupFields() {
        String s=getIntent().getStringExtra("mCatalog");
        mCatalog=Catalog.getObjectFromJSON(s);
        et_title= findViewById(R.id.et_title);
        btn=findViewById(R.id.btn_add_new_item);
        rg_category= findViewById(R.id.form_rg_category);
        rg_condition= findViewById(R.id.rg_condition);
        rg_time_period= findViewById(R.id.rg_time_period);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
    private void getCheckedRadioButtons() {
        rb_category=findViewById(rg_category.getCheckedRadioButtonId());
        rb_condition=findViewById(rg_condition.getCheckedRadioButtonId());
        rb_time_period=findViewById(rg_time_period.getCheckedRadioButtonId());
    }
}