package com.app.izhang.sideminder.view;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.izhang.sideminder.R;

public class homeListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String projNames[];

    public homeListAdapter(Activity context, String names[]) {
        super(context, R.layout.mylist, names);
        // TODO Auto-generated constructor stub

        this.projNames = names;
        this.context=context;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.projName);

        txtTitle.setText(projNames[position]);
        Log.v("Project Name", projNames[position]);
        return rowView;

    };
}