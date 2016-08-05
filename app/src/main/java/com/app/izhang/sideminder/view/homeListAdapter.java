package com.app.izhang.sideminder.view;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.izhang.sideminder.R;
import com.app.izhang.sideminder.model.Project;

import java.util.List;

public class homeListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<Project> projects;

    public homeListAdapter(Activity context, List<Project> projects, String[] projectTitles) {
        super(context, R.layout.mylist, projectTitles);
        // TODO Auto-generated constructor stub

        this.projects = projects;
        this.context=context;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.projName);

        txtTitle.setText(projects.get(position).getTitle());
        return rowView;

    }
}