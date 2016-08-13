package com.app.izhang.sideminder.presenter;

import com.app.izhang.sideminder.model.Project;

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
    public boolean addNewProject(Project project) {
        return true;
    }

    @Override
    public boolean removeProject(String projName) {
        return true;
    }
}
