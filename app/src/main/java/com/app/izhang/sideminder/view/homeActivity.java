package com.app.izhang.sideminder.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.izhang.sideminder.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class homeActivity extends AppCompatActivity implements homeView {

    ListView homeList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeList = (ListView) this.findViewById(R.id.homeList);

        homeListAdapter adapter = new homeListAdapter(this, getProjectName());

        System.out.print(getProjectName().toString());
        Log.d("Check", "test");
        
        homeList.setAdapter(adapter);

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
}
