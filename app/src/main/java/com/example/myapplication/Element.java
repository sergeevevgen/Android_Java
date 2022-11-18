package com.example.myapplication;

import androidx.annotation.NonNull;

public class Element {
    private String text;
    private final long id;
    private boolean isSelected;


    public Element(long id, String text, boolean flag){
        this.id = id;
        this.text = text;
        this.isSelected = flag;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void toggleChecked(){
        isSelected = !isSelected;
    }

    @NonNull
    @Override
    public String toString() {
        return "'" + text + "'";
    }
}
