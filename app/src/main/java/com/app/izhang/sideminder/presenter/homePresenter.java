package com.app.izhang.sideminder.presenter;

import com.app.izhang.sideminder.model.Project;

import java.util.List;

/**
 * Created by ivanzhang on 8/12/16.
 */
public interface homePresenter {

    // Method to get list of current data in database
    public List<Project> getHomeData();

    // Method to add new project into the database
    public boolean addNewProject(Project project);

    // Method to remove project from the database
    public boolean removeProject(String projName);
}
