package com.app.izhang.sideminder.presenter;

/**
 * Created by ivanzhang on 8/31/16.
 */
public interface projectPresenter {
    // Method to change interval of project
    public boolean setInterval(long projID, int projInterval);

    // Method to change deadline of project
    public boolean setDeadline(long projID, String projDeadline);

    public boolean setDescription(long projID, String projDescription);

    public boolean setHashtag(long projID, String projHashtag);

}
