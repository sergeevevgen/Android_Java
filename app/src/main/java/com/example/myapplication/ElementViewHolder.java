package com.example.myapplication;

import android.widget.CheckBox;
import android.widget.TextView;

public class ElementViewHolder {
    private CheckBox checkBox;
    private TextView textView;

    public ElementViewHolder(CheckBox checkBox, TextView textView) {
        this.checkBox = checkBox;
        this.textView = textView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
