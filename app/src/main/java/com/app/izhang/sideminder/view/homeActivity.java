/**
 * homeActivity.java
 * Created By: Ivan Zhang
 * Purpose:
 *  Home page UI design, initialization and interaction. Passes off DB related tasks to the presenter.
 */

package com.app.izhang.sideminder.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.format.DateFormat;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.izhang.sideminder.R;
import com.app.izhang.sideminder.model.Project;
import com.app.izhang.sideminder.presenter.homePresenterImpl;
import com.daimajia.swipe.SwipeLayout;
import com.orm.SugarContext;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class homeActivity extends AppCompatActivity implements homeView, FloatingActionButton.OnClickListener{

    static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";
    static final String PROJECT_KEY = "PROJ_ID";

    private ListView homeList = null;
    private FloatingActionButton addProjFab = null;
    private SimpleDateFormat sdf;
    private Calendar deadlineDate;
    private DatePickerDialog deadlinePicker;
    private TimePickerDialog timePicker;
    private EditText projDeadlineDate;
    private EditText projTime;
    private homeListAdapter adapter;
    private long reminderTimeInMilli;

    private homePresenterImpl presenter;
    List<Project> projectsLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SugarContext.init(this);
        homeList = (ListView) this.findViewById(R.id.homeList);
        addProjFab = (FloatingActionButton) this.findViewById(R.id.homeFab);
        presenter = new homePresenterImpl();

        setAdapter();

        addProjFab.setOnClickListener(this);

        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), projectActivity.class);
                intent.putExtra(PROJECT_KEY, projectsLists.get(i).getId());
                Log.v("Project Int", "int: " + i );
                startActivity(intent);
            }
        });

        createDatePickerDialog();
        createTimePickerDialog();

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.addproject_dialog,null);

        final EditText projNameTV = (EditText) dialogView.findViewById(R.id.projName);
        final EditText projDescriptionTV = (EditText) dialogView.findViewById(R.id.projDesc);
        final EditText projInterval = (EditText) dialogView.findViewById(R.id.projInterval);

        // Project Time Init
        projTime = (EditText) dialogView.findViewById(R.id.projTime);
        projTime.setInputType(InputType.TYPE_NULL);
        projTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.show();
            }
        });

        // Deadline Init
        projDeadlineDate = (EditText) dialogView.findViewById(R.id.projectDeadline);
        projDeadlineDate.setInputType(InputType.TYPE_NULL);
        projDeadlineDate.requestFocus();
        projDeadlineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deadlinePicker.show();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Add New Project");

        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String date = sdf.format(deadlineDate.getTime());
                saveData(projNameTV.getText().toString(), projDescriptionTV.getText().toString(),projInterval.getText().toString(), date, reminderTimeInMilli);
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


    public void createDatePickerDialog() {
        Calendar newCalendar = Calendar.getInstance();
        deadlinePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                deadlineDate = Calendar.getInstance();
                deadlineDate.set(year, monthOfYear, dayOfMonth);
                String date = sdf.format(deadlineDate.getTime());
                date = date.substring(0,10);
                projDeadlineDate.setText(date);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public void createTimePickerDialog(){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        timePicker = new TimePickerDialog(this,
                 new TimePickerDialog.OnTimeSetListener() {
                     @Override
                     public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                         String ampm = "AM";
                         int hourShown = hourOfDay;
                         if(hourShown > 12){
                             hourShown = hourShown - 12;
                             ampm = "PM";
                         }

                         if(minute == 0) projTime.setText(hourShown + ":" + "00" + " " + ampm);
                         else if(minute < 10) projTime.setText(hourShown + ":" + "0" + minute + " " + ampm);
                         else projTime.setText(hourShown + ":" + minute + " " + ampm);

                         Calendar calendar = Calendar.getInstance();
                         calendar.set(Calendar.HOUR, hourOfDay);
                         calendar.set(Calendar.MINUTE, minute);
                         reminderTimeInMilli = calendar.getTimeInMillis();

                     }
                 },
                 hour,
                 minute,
                 DateFormat.is24HourFormat(this));

    }

    public void saveData(String projName, String projDescription, String projInterval, String projDeadline, long projReminderTime){
        boolean added = presenter.addNewProject(projName, projDescription, projInterval, projDeadline, projReminderTime);
        if(added){
            setAdapter();
        }else{
            Toast.makeText(getApplicationContext(), "Please add the project again. An error occured.",Toast.LENGTH_LONG).show();
        }
    }


    public void setAdapter(){
        // TODO: 7/28/16 Pretty crude way of doing this...seek alternate way if possible
        projectsLists = presenter.getHomeData();
        String temp[] = new String[projectsLists.size()];
        for(int i = 0; i < projectsLists.size(); i++){
            temp[i] = projectsLists.get(i).getTitle();
        }
        adapter = new homeListAdapter(this, projectsLists, temp);
        homeList.setAdapter(adapter);
    }

    public void deleteProject(Project proj){
        homePresenterImpl presenter = new homePresenterImpl();
        presenter.removeProject(proj.getId());
        setAdapter();
    }

}
