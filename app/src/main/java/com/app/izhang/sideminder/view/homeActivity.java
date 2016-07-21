package com.app.izhang.sideminder.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
        AlertDialog.Builder addProjectPopup = new AlertDialog.Builder(this);

    }
}
