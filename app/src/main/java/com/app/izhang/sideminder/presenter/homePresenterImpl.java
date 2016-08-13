package com.app.izhang.sideminder.presenter;

import android.util.Log;
import android.widget.Toast;

import com.app.izhang.sideminder.model.Project;

import java.util.Date;
import java.util.List;

/**
 * Created by ivanzhang on 8/12/16.
 */
public class homePresenterImpl implements homePresenter{
    @Override
    public List<Project> getHomeData() {
        List<Project> list = Project.listAll(Project.class);
        return list;
    }

    @Override
    public boolean addNewProject(String projName, String projDescription, String projInterval, String projDeadline) {

        // TODO: 8/13/16 Verify that the data isn't already in the system.

        Date date = new Date(projDeadline);
        int projIntInterval = Integer.parseInt(projInterval);
        Project newProject = new Project(date, projIntInterval, projName, projDescription);
        long saveLog = newProject.save();
        Log.v("SaveLog", "Long Number : " +saveLog);
        return true;
    }

    @Override
    public boolean removeProject(String projName) {
        return true;
    }
}
