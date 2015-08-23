package org.saarang.instieventsapp.Objects;

import java.util.List;

/**
 * Created by kiran on 23/8/15.
 */
public class Club {
    String id;
    String name;
    String createdOn;
    String updatedOn;
    String description;
    String category;
    Boolean isSubscribed;
    class Convenors{

        String name;
        String phonenum;
        String email;
    }
    List<Convenors> convenorsList;

    public Club(String hostelname,Boolean subscribed){
        this.name=hostelname;
        this.isSubscribed=subscribed;
    }

    public String getName(){
        return name;
    }

    public Boolean getIsSubscribed(){
        return isSubscribed;
    }

    public String getId(){
        return id;
    }

    public String getCreatedOn(){
        return createdOn;
    }

    public String getUpdatedOn(){
        return updatedOn;
    }

    public String getDescription(){
        return description;
    }

    public String getCategory(){
        return category;
    }

    public List<Convenors> getConvenorsList(){
        return convenorsList;
    }
    public void setIsSubscribed(Boolean subscribed){
        this.isSubscribed=subscribed;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setId(String id){
        this.id=id;
    }

    public void setCreatedOn(String createdon){
        this.createdOn=createdon;
    }

    public void setUpdatedOn(String updatedon){
        this.updatedOn=updatedon;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public void setCategory(String category){
        this.category=category;
    }

    public void setConvenorsList(List<Convenors> list){
        this.convenorsList=list;
    }
}
