package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        //Метод добавления элементов в лист с выбором только одного элемента
        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(view -> {
            EditText ed = findViewById(R.id.editText);
            if (!ed.getText().toString().equals(""))
            {
                list.add(0,ed.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }
}