/**
 * homePresenter.java
 * Created By: Ivan Zhang
 * Purpose:
 *  Interface to match homeActivity data interaction
 */
package com.app.izhang.sideminder.presenter;

import com.app.izhang.sideminder.model.Project;

import java.util.List;


public interface homePresenter {

    // Method to get list of current data in database
    List<Project> getHomeData();

    // Method to add new project into the database
    boolean addNewProject(String projName, String projDescription, String projInterval, String projDeadline);

    // Method to remove project from the database
    boolean removeProject(long projId);


}
