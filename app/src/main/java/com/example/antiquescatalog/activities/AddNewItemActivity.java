package com.example.antiquescatalog.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.antiquescatalog.models.Catalog;
import com.example.antiquescatalog.models.Item;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.antiquescatalog.R;

public class AddNewItemActivity extends AppCompatActivity {

    //region Properties
    private Bitmap mBitmap;
    private Catalog mCatalog;
    private EditText et_title, et_note;
    private RadioGroup rg_category, rg_condition, rg_time_period;
    private RadioButton rb_category, rb_condition, rb_time_period;
    private Button btn_add_item;//, btn_upload_image, btn_take_picture;
    //private ImageView iv_image;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_CAMERA_PERMISSIONS = 0;
    private static final String[] CAMERA_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA};
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        setupToolBar();
        setupFields();
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddNewItemClick();
            }
        });
//        btn_upload_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(
//                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
//
//            }
//        });
//        btn_take_picture.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                if (hasCameraPermission()) {
//                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                    startActivity(intent);
//                } else {
//                    requestPermissions(CAMERA_PERMISSIONS, REQUEST_CAMERA_PERMISSIONS);
//                }
//            }
//        });
    }



    private void handleAddNewItemClick() {
        getCheckedRadioButtons();
        MainActivity.newItem = new Item(et_title.getText().toString(), rb_category.getText().toString(),
                rb_time_period.getText().toString(), rb_condition.getText().toString(), et_note.getText().toString());
        finish();
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setupFields() {
        String s = getIntent().getStringExtra("mCatalog");
        mCatalog = Catalog.getObjectFromJSON(s);
        et_title = findViewById(R.id.et_title);
        et_note=findViewById(R.id.et_note);
        btn_add_item = findViewById(R.id.btn_add_new_item);
//        btn_upload_image = findViewById(R.id.btn_upload_image_add_new_item_form);
//        btn_take_picture = findViewById(R.id.btn_take_picture_add_new_item_form);
        rg_category = findViewById(R.id.form_rg_category);
        rg_condition = findViewById(R.id.rg_condition);
        rg_time_period = findViewById(R.id.rg_time_period);
//        iv_image = findViewById((R.id.iv_add_new_item_form));
//        iv_image.setImageResource(R.drawable.ic_no_image);
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
        rb_category = findViewById(rg_category.getCheckedRadioButtonId());
        rb_condition = findViewById(rg_condition.getCheckedRadioButtonId());
        rb_time_period = findViewById(rg_time_period.getCheckedRadioButtonId());
    }

    private boolean hasCameraPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}