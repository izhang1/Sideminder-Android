/**
 * Project.java
 * Created By: Ivan Zhang
 * Purpose:
 *  Model that interacts directly with the SQL database through SugarORM.
 **/

package com.app.izhang.sideminder.model;

import android.util.Log;
import android.widget.Toast;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Project extends SugarRecord {
    private Date dueDate; // When the Project should be due
    private int reminderInterval; // The interval that the user should be notificed of the Project
    private String title; // Name of the side Project
    private String description; // Description of side Project
    private String jsonHashtags;
    private long reminderTimeInMilli;

    private String HASHTAG_ID = "HASHTAGS";

    public Project(){}

    public Project(Date dueDate, int reminderInterval, long reminderTimeInMilli, String title, String description, ArrayList hashTags){
        this.dueDate = dueDate;
        this.reminderInterval = reminderInterval;
        this.title = title;
        this.description = description;
        this.reminderTimeInMilli = reminderTimeInMilli;

        // Save ArrayList as JSON String
        try {
            JSONObject json = new JSONObject();
            json.put(HASHTAG_ID, new JSONArray(hashTags));
            jsonHashtags = json.toString();
        }catch(Exception e){
            Log.e("Error Occured", e.toString());
        }
    }

    // Getters

    public Date getDueDate() {
        return dueDate;
    }

    public int getReminderInterval() {
        return reminderInterval;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getReminderTimeInMilli() {
        return reminderTimeInMilli;
    }


    // Setters

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setReminderInterval(int reminderInterval) {
        this.reminderInterval = reminderInterval;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addHashtag(String hashtags){

    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Project && ((Project) o).description == description && ((Project) o).title == title){
            return true;
        }

        return false;
    }

    @Override
    public String toString(){
        return "Title: " + title + "\n Description: " + description + " \n Due on: " + dueDate.toString();
    }


    public String getJsonHashtags() {
        return jsonHashtags;
    }

    public void setJsonHashtags(String jsonHashtags) {
        this.jsonHashtags = jsonHashtags;
    }
}
