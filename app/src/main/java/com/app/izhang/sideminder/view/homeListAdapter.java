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
import com.app.izhang.sideminder.presenter.homePresenterImpl;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.List;

public class homeListAdapter extends ArraySwipeAdapter<String> {

    private final Activity context;
    private final List<Project> projects;

    public homeListAdapter(Activity context, List<Project> projects, String[] projectTitles) {
        super(context, R.layout.mylist, projectTitles);
        // TODO Auto-generated constructor stub

        this.projects = projects;
        this.context=context;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        final TextView txtTitle = (TextView) rowView.findViewById(R.id.projName);
        final TextView deleteText = (TextView) rowView.findViewById(R.id.deleteText);

        final String projName = projects.get(position).getTitle();
        txtTitle.setText(projName);

        final Project proj = projects.get(position);
        deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((homeActivity)context).deleteProject(proj);
            }
        });

        return rowView;
    }

    @Override
    public int getSwipeLayoutResourceId(int position){
        return R.id.swipelayout;
    }





}