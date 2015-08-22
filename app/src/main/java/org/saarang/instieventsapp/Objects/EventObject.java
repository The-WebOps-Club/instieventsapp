package org.saarang.instieventsapp.Objects;

import com.google.gson.Gson;

/**
 * Created by Seetharaman on 22-08-2015.
 */
public class EventObject {

    String name, time, venue, description, results, details, club, createdOn, updatedOn;
    boolean isActive, isLitSocEvent;

    Gson gson = new Gson();

    public EventObject(String name, String time, String venue, String description, String results, String details, boolean isActive,
                       boolean isLitSocEvent, String club, String createdOn, String updatedOn){

        this.name = name;
        this.time = time;
        this.venue = venue;
        this.description = description;
        this.results = results;
        this.details = details;
        this.isActive = isActive;
        this.isLitSocEvent = isLitSocEvent;
        this.club = club;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;

    }

    public String getName(){
        return name;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getVenue(){
        return venue;
    }

    public String getDescription(){
        return description;
    }

    public String getResults(){
        return results;
    }

    public String getDetails(){
        return details;
    }

    public boolean isActive(){
        return isActive;
    }

    public boolean isLitSocEvent(){
        return isLitSocEvent;
    }

    public String getClub(){
        return club;
    }

    public String getCreatedOn(){
        return createdOn;
    }

    public String getUpdatedOn(){
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn){
        this.updatedOn = updatedOn;
    }
}
