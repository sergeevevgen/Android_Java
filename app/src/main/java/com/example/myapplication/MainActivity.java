package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Pair;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new ViewModelProvider(this).get(MainViewModel.class);
        initUI();
        addObservers();
        //Метод добавления элементов в лист с выбором только одного элемента
        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(view -> {
            EditText ed = findViewById(R.id.editText);
            if (!ed.getText().toString().equals(""))
            {
                model.addListItems(ed.getText().toString());
            }
        });

        Button buttonToast = findViewById(R.id.buttonToast);
        buttonToast.setOnClickListener(view -> {
            Toast.makeText(this, model.getSelectedItems().getValue().toString(), Toast.LENGTH_SHORT).show();
        });
    }

    private void addObservers() {
        ListView listView = findViewById(R.id.listView);
        model.getListItems().observe(this, l -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>
                    (this, android.R.layout.simple_list_item_multiple_choice, model.getListItems().getValue());
            listView.setAdapter(adapter);
        });
    }
    private void initUI(){
        ListView listView = findViewById(R.id.listView);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            if (model.getListItems().getValue() != null)
            {
                SparseBooleanArray selected = listView.getCheckedItemPositions();

                ArrayList<String> selectedItems1 = new ArrayList<>();
                ArrayList<String> elements1 = model.getListItems().getValue();
                for (int i = 0; i < elements1.size() && i < selected.size(); ++i)
                {
                    if (selected.valueAt(i))
                        selectedItems1.add(elements1.get(i));
                }
                model.setSelectedItems(selectedItems1);
            }
        });
    }
    public void onCheckboxClicked(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        ArrayList<String> selectedItems = model.getSelectedItems().getValue();
        ArrayList<String> elements = model.getListItems().getValue();
        if (view.getId() == R.id.checkBoxSelectAll) {
            ListView listView = findViewById(R.id.listView);
            if (checked) {
                for (int i = 0; i < model.getListItems().getValue().size(); ++i)
                {
                    listView.setItemChecked(i, true);
                }
                model.setSelectedItems(model.getListItems().getValue());
            }
            else
            {
                for (int i = 0; i < model.getSelectedItems().getValue().size(); ++i)
                {
                    listView.setItemChecked(i, false);
                }
                model.setSelectedItems(null);
            }
        }
    }
}