package com.app.izhang.sideminder.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.app.izhang.sideminder.R;
import com.app.izhang.sideminder.model.Project;

import java.util.ArrayList;

public class projectActivity extends AppCompatActivity {

    static final String PROJECT_KEY = "PROJ_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        long projID = 0;

        if(getIntent().hasExtra(PROJECT_KEY)){
            projID = getIntent().getLongExtra(PROJECT_KEY, 0);
        }else{
            throw new IllegalArgumentException("Activity does not contain project ID");
        }

        Project project = Project.findById(Project.class, projID);

        setTitle("Project: " + project.getTitle());

        ArrayList hashTags = new ArrayList();
        hashTags.add("#Projec1");
        hashTags.add("#Android");

        ListView listview = (ListView) this.findViewById(R.id.projInfoList);
        listview.setAdapter(new projectInfoListAdapter(this, project.getDueDate().toString(),Integer.toString(project.getReminderInterval()),hashTags));

    }
}
