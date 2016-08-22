package com.app.izhang.sideminder.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.app.izhang.sideminder.R;
import com.app.izhang.sideminder.model.Project;
import com.app.izhang.sideminder.presenter.homePresenterImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class projectActivity extends AppCompatActivity {

    static final String PROJECT_KEY = "PROJ_ID";
    Project project = null;

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

        project = Project.findById(Project.class, projID);

        setTitle("Project: " + project.getTitle());

        ArrayList hashTags = new ArrayList();
        hashTags.add("#Projec1");
        hashTags.add("#Android");

        ListView listview = (ListView) this.findViewById(R.id.projInfoList);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd");
        Date date = project.getDueDate();

        listview.setAdapter(new projectInfoListAdapter(this,
                    sdf.format(date),
                    Integer.toString(project.getReminderInterval()),
                    hashTags));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                if(project != null){
                    homePresenterImpl presenter = new homePresenterImpl();
                    presenter.removeProject(project.getId());
                    Intent homeIntent = new Intent(getApplicationContext(), homeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
