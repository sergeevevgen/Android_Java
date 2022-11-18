package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ElementAdapter extends ArrayAdapter<Element> {
    private final LayoutInflater inflater;
    private final List<Element> objects;

    public ElementAdapter(@NonNull Context context, @NonNull List<Element> objects) {
        super(context, R.layout.item, R.id.text, objects);
        inflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Element element = objects.get(position);
        CheckBox checkBox;
        TextView textView;

        // Create a new row view
        if (convertView == null) {
            //maybe parent?
            convertView = inflater.inflate(R.layout.item, null);

            textView = convertView.findViewById(R.id.text);
            checkBox = convertView.findViewById(R.id.cb);

            convertView.setTag(new ElementViewHolder(checkBox, textView));

            // If CheckBox is toggled, update the planet it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Element element1 = (Element) cb.getTag();
                    element1.setSelected(cb.isChecked());
                }
            });
        }
        // Re-use existing row view
        else {

            ElementViewHolder viewHolder = (ElementViewHolder) convertView
                    .getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        checkBox.setTag(element);

        // Display planet data
        checkBox.setChecked(element.isSelected());
        textView.setText(element.getText());

        return convertView;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Element getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return objects.get(position).getId();
    }
}
