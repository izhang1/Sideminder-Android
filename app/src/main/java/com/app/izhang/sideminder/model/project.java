package com.app.izhang.sideminder.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by izhang on 6/21/16.
 */
public class project extends SugarRecord {
    private Date dueDate; // When the project should be due
    private int reminderInterval; // The interval that the user should be notificed of the project
    private String title; // Name of the side project
    private String description; // Description of side project

    public project(){

    }

    public project(Date dueDate, int reminderInterval, String title, String description){
        this.dueDate = dueDate;
        this.reminderInterval = reminderInterval;
        this.title = title;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if(o instanceof project && ((project) o).description == description && ((project) o).title == title){
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n Description: " + description + " \n Due on: " + dueDate.toString();
    }
}
