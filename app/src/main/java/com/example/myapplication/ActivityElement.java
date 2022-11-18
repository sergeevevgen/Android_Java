package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityElement extends AppCompatActivity {

    private EditText textBox;
    private DB db;
    private long elementId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);
        InitUI();

        db = new DB(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            elementId = extras.getLong("id");
        }
        // если 0, то добавление
        if (elementId > -1) {
            // получаем элемент по id из бд
            db.open();
            Element element = db.getElement(elementId);
            textBox.setText(element.getText());
            db.close();
        }
    }

    private void InitUI(){
        Button addButton = findViewById(R.id.saveButton);
        addButton.setOnClickListener(this::save);

        textBox = findViewById(R.id.textBoxElement);

        Button btnCancel = findViewById(R.id.cancelButton);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    public void save(View view){
        String text = textBox.getText().toString();
        Element element = new Element(elementId, text, false);

        db.open();
        if (elementId > 0) {
            db.updateElement(element);
        } else {
            db.addElement(element);
        }
        db.close();
        finish();
    }
}