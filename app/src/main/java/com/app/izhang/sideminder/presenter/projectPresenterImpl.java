package com.app.izhang.sideminder.presenter;

import com.app.izhang.sideminder.model.Project;

import java.util.Date;

/**
 * Created by ivanzhang on 8/31/16.
 */
public class projectPresenterImpl implements projectPresenter{

    @Override
    public boolean setInterval(long projID, int projInterval) {
        Project project = Project.findById(Project.class, projID);
        if(project == null) return false;

        project.setReminderInterval(projInterval);
        if(project.getReminderInterval() == projInterval) project.save();
        return true;
    }

    @Override
    public boolean setDeadline(long projID, String projDeadline) {
        Project project = Project.findById(Project.class, projID);
        if(project == null) return false;
        if(projDeadline.isEmpty()) return false;
        Date date = new Date(projDeadline);
        project.setDueDate(date);
        project.save();

        return true;
    }

    @Override
    public boolean setDescription(long projID, String projDescription) {
        Project project = Project.findById(Project.class, projID);
        if(project == null) return false;
        if(projDescription.isEmpty()) return false;

        project.setDescription(projDescription);
        project.save();

        return true;

    }
}
