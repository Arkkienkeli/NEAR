package com.example.apostol3.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Arkkienkeli on 09.07.2015.
 */
public class PersonListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PersonListItem> personListItems;

    public PersonListAdapter(Context context, ArrayList<PersonListItem> personListItems){
        this.context = context;
        this.personListItems = personListItems;
    }

    @Override
    public int getCount() {
        return personListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return personListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

}
