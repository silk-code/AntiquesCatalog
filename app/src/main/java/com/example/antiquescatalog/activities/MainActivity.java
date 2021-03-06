package com.example.antiquescatalog.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.antiquescatalog.R;
import com.example.antiquescatalog.interfaces.AdapterOnItemClickListener;
import com.example.antiquescatalog.lib.Utils;
import com.example.antiquescatalog.models.Catalog;
import com.example.antiquescatalog.models.CatalogAdapter;
import com.example.antiquescatalog.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.antiquescatalog.lib.Utils.showInfoDialog;
import static com.example.antiquescatalog.models.Catalog.getJSONFromObject;
import static com.example.antiquescatalog.models.Catalog.getObjectFromJSON;

public class MainActivity extends AppCompatActivity {

    //region Properties
    private Catalog mCatalog;
    private CatalogAdapter mCatalogAdapter;
    private ArrayList<Item> list;
    private boolean mPrefNightMode;
    private final String mKeyCatalog = "CATALOG";
    private final String path = "save.txt";
    public static Item newItem = null;
    private static final int REQUEST_STORAGE_PERMISSIONS=0;
    private static final String[] STORAGE_PERMISSIONS= new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolBar();
        setupFAB();
        setCatalog(savedInstanceState);
        restoreOrSetFromPreferences();
        setupRecyclerView();
    }

    //region Set Up Activity
    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                showAddNewItem();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showAddNewItem() {
        //dismissSnackBarIfShown();
        if(hasStoragePermission()) {
            Intent intent = new Intent(getApplicationContext(), AddNewItemActivity.class);
            intent.putExtra("mCatalog", mCatalog.getJSONFromCurrentObject());
            startActivity(intent);
        }else{
            requestPermissions(STORAGE_PERMISSIONS,REQUEST_STORAGE_PERMISSIONS);
        }
    }

    private void setupRecyclerView() {
        final int columns = getResources().getInteger(R.integer.gallery_columns);
        RecyclerView recyclerView = findViewById(R.id.rv_items);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));
        list = (ArrayList) mCatalog.getList();
        mCatalogAdapter = new CatalogAdapter(this, list);
        //mCatalogAdapter.setClickListener(this);
        recyclerView.setAdapter(mCatalogAdapter);
        mCatalogAdapter.setOnItemClickListener(getNewOnItemClickListener());
    }

    private AdapterOnItemClickListener getNewOnItemClickListener() {
        return new AdapterOnItemClickListener() {
            public void onItemClick(int position, View view) {

                Intent intent = new Intent(getApplicationContext(), ViewItemCardActivity.class);
                intent.putExtra("title", mCatalogAdapter.getItem(position).getTitle());
                intent.putExtra("category", mCatalogAdapter.getItem(position).getCategory());
                intent.putExtra("timePeriod", mCatalogAdapter.getItem(position).getTimePeriod());
                intent.putExtra("condition", mCatalogAdapter.getItem(position).getCondition());
                intent.putExtra("note", mCatalogAdapter.getItem(position).getNote());
                startActivity(intent);
            }
        };
    }


    private void restoreOrSetFromPreferences() {
        SharedPreferences sp = getDefaultSharedPreferences(this);
        mPrefNightMode = sp.getBoolean(getString(R.string.night_mode_key), true);
        Utils.setNightModeOnOrOff(mPrefNightMode);
    }

    private void setCatalog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCatalog = Catalog.getObjectFromJSON(savedInstanceState.getString(mKeyCatalog));
            return;
        } else {
            mCatalog = new Catalog();
        }

        File file = new File(getString(R.string.filepath_save));
        if (file.exists()) {
            //Read text from file
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                }
                br.close();
                Catalog c = Catalog.getObjectFromJSON(text.toString());
                mCatalog.updateList(c);
                return;
            } catch (IOException e) {
                //TODO msg to show problem
            }
        }
    }


    //endregion

    //region Menu
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
        intent.putExtra("mCatalog", mCatalog.getJSONFromCurrentObject());
        startActivityForResult(intent, 1);
    }
    //endregion

    //region Save State
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(mKeyCatalog, getJSONFromObject(mCatalog));
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
    protected void onStop() {
        writeFileOnInternalStorage(this, Catalog.getJSONFromObject(mCatalog));
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        writeFileOnInternalStorage(this, Catalog.getJSONFromObject(mCatalog));
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        writeFileOnInternalStorage(this, Catalog.getJSONFromObject(mCatalog));
        super.onPause();
    }


    public void writeFileOnInternalStorage(Context context, String textToSave) {
        try {
            File file = new File(getString(R.string.filepath_save));
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.append(textToSave);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasStoragePermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),STORAGE_PERMISSIONS[0]);
        return result== PackageManager.PERMISSION_GRANTED;
    }

    //endregion
}