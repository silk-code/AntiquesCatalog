package com.example.antiquescatalog.models;

public class Item {
    //image image;
    private String title, category, timePeriod, condition, note;

    public Item(String title, String category, String timePeriod, String condition, String note //, image
    ) {
        this.title = title;
        this.category = category;
        this.timePeriod = timePeriod;
        this.condition = condition;
        this.note = note;
    }

    public void editItem(String title, String category, String timePeriod, String condition //, image
    ) {
        this.title = title;
        this.category = category;
        this.timePeriod = timePeriod;
        this.condition = condition;
        //this.image=image;
    }

    //region Getters
    public String getTitle() {
        return this.title;
    }

    public String getCategory() {
        return this.category;
    }

    public String getTimePeriod() {
        return this.timePeriod;
    }

    public String getCondition() {
        return this.condition;
    }

    public String getNote() {
        return this.note;
    }
//    public  getImage(){
//        return this.image;
//    }
    //endregion

    //region Setters
    private void setTitle(String t) {
        this.title = t;
    }

    private void setCategory(String c) {
        this.category = c;
    }

    private void setTimePeriod(String tp) {
        this.timePeriod = tp;
    }

    private void setCondition(String c) {
        this.category = c;
    }

    private void setNote(String n) {
        this.note = n;
    }
//    private void setImage(i){
//        this.image=i;
//    }
    //endregion
}
