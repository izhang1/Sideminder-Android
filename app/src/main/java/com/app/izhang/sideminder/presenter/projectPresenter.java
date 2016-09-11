/**
 * projectPresenter.java
 * Created By: Ivan Zhang
 * Purpose:
 *  Interface to match projectActivity data interaction
 */

package com.app.izhang.sideminder.presenter;

/**
 * Created by ivanzhang on 8/31/16.
 */
public interface projectPresenter {
    // Method to change interval of project
    boolean setInterval(long projID, int projInterval);

    // Method to change deadline of project
    boolean setDeadline(long projID, String projDeadline);

    boolean setDescription(long projID, String projDescription);

    boolean setHashtag(long projID, String projHashtag);

}
