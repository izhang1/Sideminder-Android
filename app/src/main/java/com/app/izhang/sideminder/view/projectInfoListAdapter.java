package com.app.izhang.sideminder.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.izhang.sideminder.R;
import com.app.izhang.sideminder.model.Project;

import java.util.ArrayList;
import java.util.List;

public class projectInfoListAdapter extends BaseAdapter{

    Context context;
    private static LayoutInflater inflater = null;
    String date;
    String intervalTime;
    String description;
    ArrayList<String> hashtags;

    public projectInfoListAdapter(Context mainActivity, String date, String intervalTime, ArrayList<String> hashtags, String description) {
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.date = date;
        this.intervalTime = intervalTime;
        this.hashtags = hashtags;
        this.description = description;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.projinfoview, null, true);

        TextView infoTitle = (TextView) rowView.findViewById(R.id.infoTitle);
        TextView infoDetails = (TextView) rowView.findViewById(R.id.infoDetails);
        TextView infoIndicator = (TextView) rowView.findViewById(R.id.circleindicator);

        String titleContent = "";
        String detailsContent = "";

        if(position == 0){
            titleContent = "Due Date";
            detailsContent = date;
        }else if(position == 1){
            titleContent = "Notification";
            detailsContent = "Every " + intervalTime + " Days";
        }else if(position == 2){
            titleContent = "Tags";
            detailsContent = hashtags.toString();
            infoIndicator.setText("#");
        }else if(position == 3){
            titleContent = "Description";
            detailsContent = description;
        }

        infoTitle.setText(titleContent);
        infoDetails.setText(detailsContent);

        return rowView;

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

}