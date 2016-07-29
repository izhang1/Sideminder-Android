package com.app.izhang.sideminder.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.izhang.sideminder.R;
import com.app.izhang.sideminder.model.Project;
import com.orm.SugarContext;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class homeActivity extends AppCompatActivity implements homeView, FloatingActionButton.OnClickListener{

    static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";

    private ListView homeList = null;
    private FloatingActionButton addProjFab = null;
    private SimpleDateFormat sdf;
    private DatePickerDialog deadlinePicker;
    private EditText projDeadlineDate;
    private homeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SugarContext.init(this);
        homeList = (ListView) this.findViewById(R.id.homeList);
        addProjFab = (FloatingActionButton) this.findViewById(R.id.homeFab);

        setAdapter();

        addProjFab.setOnClickListener(this);

        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        sdf = new SimpleDateFormat(DATE_FORMAT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initHomePage(){
        homeList = (ListView) this.findViewById(R.id.homeList);
    }

    @Override
    public List<Project> getProjectName(){
        List<Project> list = Project.listAll(Project.class);
        return list;
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

    @Override
    public void onClick(View v){
        // Assumes FAB button was clicked
        Toast.makeText(getApplicationContext(), "FAB button clicked", Toast.LENGTH_LONG).show();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.addproject_dialog,null);

        final EditText projNameTV = (EditText) dialogView.findViewById(R.id.projName);
        final EditText projDescriptionTV = (EditText) dialogView.findViewById(R.id.projDesc);
        final Spinner projReminderInterval = (Spinner) dialogView.findViewById(R.id.projRemindInter);

        // Spinner Populate
        List<String> categories = Arrays.asList(getIntervalList());

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        projReminderInterval.setAdapter(dataAdapter);

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

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Add New Project");

        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //// TODO: 7/21/16 Add Project Data to Database
                saveData(projNameTV.getText().toString(), projDescriptionTV.getText().toString(),projReminderInterval.getSelectedItem().toString(), projDeadlineDate.getText().toString());
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Canceled Project Dialog", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
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

    private void saveData(String projName, String projDescription, String projInterval, String projDeadline){
        Date date = new Date(projDeadline);
        int projIntInterval = Integer.parseInt(projInterval);
        Project newProject = new Project(date, projIntInterval, projName, projDescription);
        newProject.save();
        setAdapter();
    }

    private void setAdapter(){
        // TODO: 7/28/16 Pretty crude way of doing this...seek alternate way if possible
        List<Project> projectsLists = getProjectName();
        String temp[] = new String[projectsLists.size()];
        for(int i = 0; i < projectsLists.size(); i++){
            temp[i] = projectsLists.get(i).getTitle();
        }
        adapter = new homeListAdapter(this, projectsLists, temp);
        homeList.setAdapter(adapter);
    }

}
