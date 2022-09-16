package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(adapter);

        Button buttonToast = findViewById(R.id.buttonToast);
        buttonToast.setOnClickListener(view -> {
            StringBuilder str = new StringBuilder();
            if (!list.isEmpty()) {
                SparseBooleanArray k = listView.getCheckedItemPositions();

                for(int i = 0; i < list.size(); ++i)
                {
                    if (k.valueAt(i))
                    {
                        str.append(list.get(i)).append("___");
                    }
                }
                if (str.length() != 0)
                    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            }
        });
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

    public void onCheckboxClicked(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        if (view.getId() == R.id.checkBoxSelectAll) {
            ListView listView = findViewById(R.id.listView);
            if (checked) {
                for (int i = 0; i < listView.getChildCount() + 1; ++i)
                    listView.setItemChecked(i, true);
            }
            else
            {
                for (int i = 0; i < listView.getChildCount() + 1; ++i)
                    listView.setItemChecked(i, false);
            }
        }
    }
}