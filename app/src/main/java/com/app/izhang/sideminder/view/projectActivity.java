package com.app.izhang.sideminder.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        final Date date = project.getDueDate();

        listview.setAdapter(new projectInfoListAdapter(this,
                    sdf.format(date),
                    Integer.toString(project.getReminderInterval()),
                    hashTags,
                    project.getDescription(),
                    projID));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        Dialog dialog = createDeadlinePresenter(getApplicationContext());
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;

                }
            }
        });

    }

    public Dialog createDeadlinePresenter(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Change Deadline")
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

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
