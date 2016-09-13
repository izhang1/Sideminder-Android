/**
 * projectPresenterImpl.java
 * Created By: Ivan Zhang
 * Purpose:
 *  Implements the projectPresenter.java interface. Interacts directly with the model to modify, save, and delete data.
 */

package com.app.izhang.sideminder.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.app.izhang.sideminder.model.Project;
import com.app.izhang.sideminder.view.homeActivity;
import com.app.izhang.sideminder.view.projectActivity;
import com.app.izhang.sideminder.view.showNotification;

import java.util.Date;

/**
 * Created by ivanzhang on 8/31/16.
 */
public class projectPresenterImpl implements projectPresenter{

    // This value is defined and consumed by app code, so any value will work.
    // There's no significance to this sample using 0.
    public static final int REQUEST_CODE = 0;

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

    @Override
    public boolean setHashtag(long projID, String projHashtag) {
        Project project = Project.findById(Project.class, projID);
        if(project == null) return false;
        if(projHashtag.isEmpty()) return false;

        String hashRevised = projHashtag;

        project.setHashtags(hashRevised);
        project.save();

        return true;
    }

    @Override
    public boolean setNotification(Context context, int intervalDay){
        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we call the method inside onRecieve() method pf Alarmreciever class


        Intent intentAlarm = new Intent(context, showNotification.class);
        // create the objt
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long startTime = System.currentTimeMillis();         // notification time

        long time = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        //set the alarm for particular time
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,startTime, time , PendingIntent.getBroadcast(context,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

        return true;
    }
}
