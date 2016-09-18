

package com.app.izhang.sideminder.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.app.izhang.sideminder.R;
import com.app.izhang.sideminder.model.Project;
import com.app.izhang.sideminder.presenter.homePresenterImpl;
import com.app.izhang.sideminder.presenter.projectPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class projectActivity extends AppCompatActivity {

    static final String PROJECT_KEY = "PROJ_ID";
    static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";

    private DatePickerDialog deadlinePicker;
    private EditText projDeadlineDate;
    private Project project = null;
    private SimpleDateFormat sdf;
    private long projID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        projID = 0;

        if(getIntent().hasExtra(PROJECT_KEY)){
            projID = getIntent().getLongExtra(PROJECT_KEY, 0);
        }else{
            throw new IllegalArgumentException("Activity does not contain project ID");
        }

        sdf = new SimpleDateFormat(DATE_FORMAT);


        initView();

    }

    private void initView(){
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
                project.getHashtags(),
                project.getDescription(),
                projID));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        Dialog deadlineDialog = createDeadlinePresenter();
                        deadlineDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationProject;
                        deadlineDialog.show();
                        break;
                    case 1:
                        Dialog notifyDialog = createNotificationPresenter();
                        notifyDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationProject;
                        notifyDialog.show();
                        break;
                    case 2:
                        Dialog hashtagDialog = createHashtagPresenter();
                        hashtagDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationProject;
                        hashtagDialog.show();
                        break;
                    case 3:
                        Dialog descDialog = createDescriptionPresenter();
                        descDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationProject;
                        descDialog.show();
                        break;
                }
            }
        });


    }

    // TODO: 9/15/16 Implement the set time on the time edittext and get input from time and interval
    public Dialog createNotificationPresenter(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.setnotification_dialog,null);

        EditText projectInterval = (EditText) dialogView.findViewById(R.id.projInterval);
        EditText projTime = (EditText) dialogView.findViewById(R.id.projTime);


        builder.setView(dialogView);
        builder.setMessage("Change Deadline")
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        projectPresenterImpl presenter = new projectPresenterImpl();
                        //boolean didSetInterval = presenter.setInterval(projID, Integer.parseInt(projIntervalReminder.getSelectedItem().toString()));
                        //presenter.setNotification(getApplicationContext(), 10);
                        //if(didSetInterval  ) initView();
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

    public String[] getIntervalList(){
        String[] list = {
                "1",
                "2",
                "3",
                "5",
                "7",
                "14",
                "30",
                "60",
        };

        return list;
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        deadlinePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                projDeadlineDate.setText(sdf.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        deadlinePicker.show();
    }

    public Dialog createDeadlinePresenter(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.setdeadline_dialog,null);

        // Deadline Init
        projDeadlineDate = (EditText) dialogView.findViewById(R.id.projectDeadline);
        projDeadlineDate.setInputType(InputType.TYPE_NULL);
        projDeadlineDate.requestFocus();
        projDeadlineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeField();
            }
        });

        builder.setView(dialogView);
        builder.setMessage("Change Deadline")
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        projectPresenterImpl projectPresenter = new projectPresenterImpl();
                        boolean didSetDeadline  = projectPresenter.setDeadline(projID, projDeadlineDate.getText().toString());
                        if(didSetDeadline) initView();
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

    public Dialog createDescriptionPresenter(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.setdeadline_dialog,null);

        // Deadline Init
        final EditText descriptionInput = (EditText) dialogView.findViewById(R.id.projectDeadline);
        descriptionInput.setHint("New Project Description");
        descriptionInput.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(dialogView);
        builder.setMessage("Change Description")
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        projectPresenterImpl projectPresenter = new projectPresenterImpl();
                        boolean didSetDescription  = projectPresenter.setDescription(projID, descriptionInput.getText().toString());
                        if(didSetDescription) initView();
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

    public Dialog createHashtagPresenter(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.setdeadline_dialog,null);

        // Deadline Init
        final EditText hashTagInput = (EditText) dialogView.findViewById(R.id.projectDeadline);
        hashTagInput.setText(Project.findById(Project.class, projID).getHashtags());
        hashTagInput.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(dialogView);
        builder.setMessage("Set Hashtags - Separated by Commas")
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        projectPresenterImpl projectPresenter = new projectPresenterImpl();
                        boolean didSetHashtag  = projectPresenter.setHashtag(projID, hashTagInput.getText().toString());
                        if(didSetHashtag) initView();
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
