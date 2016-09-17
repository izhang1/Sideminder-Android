/**
 * homeView.java
 * Created By: Ivan Zhang
 * Purpose:
 *  Interface for homeActivity.
 */

package com.app.izhang.sideminder.view;

import com.app.izhang.sideminder.model.Project;

/**
 * Created by ivanzhang on 7/13/16.
 */
public interface homeView {

    void createDatePickerDialog();

    void createTimePickerDialog();

    void saveData(String projName, String projDescription, String projInterval, String projDeadline, long projReminderTime);

    void setAdapter();

    void deleteProject(Project proj);

}
