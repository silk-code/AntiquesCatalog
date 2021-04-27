package com.example.antiquescatalog.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.antiquescatalog.R;
import com.example.antiquescatalog.models.Catalog;
import com.example.antiquescatalog.models.CatalogAdapter;
import com.example.antiquescatalog.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.antiquescatalog.lib.Utils.showInfoDialog;
import static com.example.antiquescatalog.models.Catalog.getJSONFromObject;
import static com.example.antiquescatalog.models.Catalog.getObjectFromJSON;

public class MainActivity extends AppCompatActivity {
    private Catalog mCatalog;
    private CatalogAdapter mCatalogAdapter;
    private ArrayList<Item> list;
    //private final String mKeyCheckedPiles = "";
    private final String mKeyCatalog = "CATALOG";
    public static Item newItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolBar();
        setupFAB();
        mCatalog = new Catalog();

        final int columns = getResources().getInteger(R.integer.gallery_columns);
        RecyclerView recyclerView = findViewById(R.id.rv_items);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));

        list = (ArrayList) mCatalog.getList();
        mCatalogAdapter = new CatalogAdapter(this, list);

        //mCatalogAdapter.setClickListener(this);
        recyclerView.setAdapter(mCatalogAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //if coming from AddNewItemActivity, there's an update
        if (newItem != null) {
            mCatalog.addItem(newItem);
            newItem = null;
            list = (ArrayList) mCatalog.getList();
        }
        mCatalogAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_about:
                showAbout();
                return true;
            case R.id.menu_settings:
                showSettings();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        //dismissSnackBarIfShown();
        showInfoDialog(MainActivity.this, "About Antiques Catalog",
                "Keep all your antiques in one place!\n" +
                        "To add: press '+' in lower right corner\n" +
                        "\nMade by KS.\nsilk2work@gmail");
    }

    private void showSettings() {
        //dismissSnackBarIfShown();
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivityForResult(intent, 1);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == 1) {
//            //restoreOrSetFromPreferences_AllAppAndGameSettings();
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mCatalog = getObjectFromJSON(savedInstanceState.getString(mKeyCatalog));
//        //updateUI();
//    }


    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNewItem();
            }
        });
    }

    private void showAddNewItem() {
        //dismissSnackBarIfShown();
        Intent intent = new Intent(getApplicationContext(), AddNewItemActivity.class);
        intent.putExtra("mCatalog", mCatalog.getJSONFromCurrentObject());
        startActivity(intent);
    }

}