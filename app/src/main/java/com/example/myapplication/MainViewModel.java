package com.example.myapplication;

import android.widget.ArrayAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> listItems;
    private MutableLiveData<ArrayList<String>> selectedItems;

    public MutableLiveData<ArrayList<String>> getSelectedItems() {
        if (selectedItems == null)
            selectedItems = new MutableLiveData<>();
        if (selectedItems.getValue() == null)
            selectedItems.setValue(new ArrayList<>());
        return selectedItems;
    }

    public void setSelectedItems(ArrayList<String> selectedItems)
    {
        if (this.selectedItems == null)
            this.selectedItems = new MutableLiveData<>();
        this.selectedItems.setValue(selectedItems);
    }

    public LiveData<ArrayList<String>> getListItems() {
        if (listItems == null)
        {
            listItems = new MutableLiveData<>();
        }
        return listItems;
    }

    public void addListItems(String itemName){
        if (listItems == null) {
            listItems = new MutableLiveData<>();
        }
        ArrayList<String> list = listItems.getValue();
        if (list == null)
            list = new ArrayList<>();
        list.add(itemName);
        listItems.setValue(list);
    }
}
