/**
 * homeActivity.java
 * Created By: Ivan Zhang
 * Purpose:
 *  Home page UI design, initialization and interaction. Passes off DB related tasks to the presenter.
 */

package com.app.izhang.sideminder.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
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
    private DatePickerDialog deadlinePicker;
    private EditText projDeadlineDate;
    private homeListAdapter adapter;

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
        Toast.makeText(getApplicationContext(), "FAB button clicked", Toast.LENGTH_LONG).show();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.addproject_dialog,null);

        final EditText projNameTV = (EditText) dialogView.findViewById(R.id.projName);
        final EditText projDescriptionTV = (EditText) dialogView.findViewById(R.id.projDesc);
        final EditText projReminderInterval = (EditText) dialogView.findViewById(R.id.projRemindInter);
        final EditText projInterval = (EditText) dialogView.findViewById(R.id.projInterval);
        final EditText projTime = (EditText) dialogView.findViewById(R.id.projTime);

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
                saveData(projNameTV.getText().toString(), projDescriptionTV.getText().toString(),projReminderInterval.getText().toString(), projDeadlineDate.getText().toString());
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


    public void setDateTimeField() {
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

    public void saveData(String projName, String projDescription, String projInterval, String projDeadline){
        boolean added = presenter.addNewProject(projName, projDescription, projInterval, projDeadline);
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
