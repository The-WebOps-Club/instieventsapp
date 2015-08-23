package org.saarang.instieventsapp.Objects;

/**
 * Created by kiran on 23/8/15.
 */
public class ClubSubscriptionObject {
    String hostelname;
    Boolean subscribed;

    public ClubSubscriptionObject(String hostelname,Boolean subscribed){
        this.hostelname=hostelname;
        this.subscribed=subscribed;
    }

    public String gethostelname(){
        return hostelname;
    }

    public void setHostelname(String hostelname){
        this.hostelname=hostelname;
    }

    public Boolean getSubscribed(){
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed){
        this.subscribed=subscribed;
    }
}
