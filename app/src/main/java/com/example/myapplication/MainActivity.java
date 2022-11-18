package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView elementList;
    private Button btnAdd, btnEdit, btnRem, btnSelAll, btnDSelAll, btnFind;
    //заменить на свой адаптер
    private ArrayAdapter<Element> arrayAdapter;
    private DB db;
    private SparseBooleanArray spb;
    private List<Element> elements;
    private String str = "logsS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initUI();

        db = new DB(this);
        db.open();

        elements = db.getElements();

        arrayAdapter = new ElementAdapter(this, elements);
        elementList.setAdapter(arrayAdapter);
        elementList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    private void initUI() {
        //Кнопка добавить
        btnAdd = findViewById(R.id.btnAddNew_m2);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityElement.class);
                startActivity(intent);
            }
        });

        //Кнопка редактировать
        btnEdit = findViewById(R.id.btnEditSel_m2);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray spb = elementList.getCheckedItemPositions();

                Element element = arrayAdapter.getItem(spb.keyAt(spb.size() - 1));

                if(element != null) {
                    Intent intent = new Intent(getApplicationContext(), ActivityElement.class);
                    intent.putExtra("id", element.getId());
                    startActivity(intent);
                }
            }
        });

        //Кнопка убрать выбранные
        btnDSelAll = findViewById(R.id.btnDeselect_m2);
        btnDSelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray spb = elementList.getCheckedItemPositions();

                for(int i = 0; i < spb.size(); ++i){
                    if (spb.valueAt(i)){
                        Element element = arrayAdapter.getItem(i);

                        element.toggleChecked();

                        ElementViewHolder holder = (ElementViewHolder) view.getTag();
                        holder.getCheckBox().setChecked(element.isSelected());
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });

        //Кнопка выбрать всё
        btnSelAll = findViewById(R.id.btnSelect_m2);
        btnSelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < arrayAdapter.getCount(); i++) {
                    Element element = arrayAdapter.getItem(i);
                    element.setSelected(true);

                    ElementViewHolder holder = (ElementViewHolder) view.getTag();
                    holder.getCheckBox().setChecked(element.isSelected());
                }
            }
        });

        //Кнопка удалить
        btnRem = findViewById(R.id.btnDelSel_m2);
        btnRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray spb = elementList.getCheckedItemPositions();

                for(int i = 0; i < spb.size(); ++i){
                    if (spb.valueAt(i)){
                        int k = (int) arrayAdapter.getItemId(i);
                        db.deleteElement(k);
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });

        //Лист
        elementList = findViewById(R.id.list_view_m2);
        elementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Element element = arrayAdapter.getItem(position);
                element.toggleChecked();

                ElementViewHolder holder = (ElementViewHolder) view.getTag();
                holder.getCheckBox().setChecked(element.isSelected());

                Log.d(str, "element with id " + element.getId() + " is " + element.isSelected());
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        elements = db.getElements();
        arrayAdapter = new ElementAdapter(this, elements);
        elementList.setAdapter(arrayAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        //нельзя сохранять всё каждый разЁ!!!!!
        for (Element element: elements) {
            db.updateElement(element);
        }
        db.close();
    }
}