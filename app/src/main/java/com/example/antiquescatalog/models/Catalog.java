package com.example.antiquescatalog.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private ArrayList<Item> itemArrayList;
    private int count;

    public Catalog() {
        itemArrayList=new ArrayList<>();
        count = 0;
    }


    public void addItem(
            String title, String category, String timePeriod, String condition //, image
    ) {
        itemArrayList.add(new Item(title,category,timePeriod,condition));
        count++;
        System.out.println(count);
    }
    public void addItem (Item i){
        itemArrayList.add(i);
    }

    public List<Item> getList(){
        return itemArrayList;
    }

    public static String getJSONFromObject (Catalog obj)
    {
        Gson gson = new Gson ();
        return gson.toJson (obj);
    }

    public  String getJSONFromCurrentObject()
    {
        return getJSONFromObject(this);
    }

    public static Catalog getObjectFromJSON (String json)
    {
        Gson gson = new Gson ();
        return gson.fromJson (json, Catalog.class);
    }

}
