package com.app.izhang.sideminder.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.izhang.sideminder.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class homeActivity extends AppCompatActivity implements homeView, FloatingActionButton.OnClickListener{

    private ListView homeList = null;
    private FloatingActionButton addProjFab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeList = (ListView) this.findViewById(R.id.homeList);
        addProjFab = (FloatingActionButton) this.findViewById(R.id.homeFab);

        homeListAdapter adapter = new homeListAdapter(this, getProjectName());
        homeList.setAdapter(adapter);

        addProjFab.setOnClickListener(this);

        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
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
    public String[] getProjectName(){
        String[] list ={
                "Project 1",
                "Project 2",
                "Project 3",
                "Project 4",
                "Project 5",
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

        final TextView projNameTV = (TextView) dialogView.findViewById(R.id.projName);
        final TextView projDescriptionTV = (TextView) dialogView.findViewById(R.id.projDesc);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Add New Project");

        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //// TODO: 7/21/16 Add Project Data to Database
                String projectName = projNameTV.getText().toString();
                String projectDescription = projDescriptionTV.getText().toString();
                Toast.makeText(getApplicationContext(), "Made new project, name: " + projectName, Toast.LENGTH_LONG).show();
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
}
