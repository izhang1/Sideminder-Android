package com.app.izhang.sideminder.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.app.izhang.sideminder.R;

import java.util.ArrayList;

public class projectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        ArrayList<String> temp = new ArrayList<>();
        temp.add("#Ello");
        ListView listview = (ListView) this.findViewById(R.id.projInfoList);
        listview.setAdapter(new projectInfoListAdapter(this, "tempdate","tempinterval",temp));

    }
}
