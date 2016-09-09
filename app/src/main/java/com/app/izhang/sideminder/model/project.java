package com.app.izhang.sideminder.model;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by izhang on 6/21/16.
 */
public class Project extends SugarRecord {
    private Date dueDate; // When the Project should be due
    private int reminderInterval; // The interval that the user should be notificed of the Project
    private String title; // Name of the side Project
    private String description; // Description of side Project
    private String hashtags;

    public Project(){}

    public Project(Date dueDate, int reminderInterval, String title, String description, String hashTags){
        this.dueDate = dueDate;
        this.reminderInterval = reminderInterval;
        this.title = title;
        this.description = description;
        this.hashtags = hashTags;
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

    public String getHashtags(){ return hashtags; }



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

    public void addHashtag(String hashtag){ this.hashtags += hashtag + ","; }

    public void setHashtags(String hashtag){ this.hashtags = hashtag; }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Project && ((Project) o).description == description && ((Project) o).title == title){
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n Description: " + description + " \n Due on: " + dueDate.toString();
    }

}
