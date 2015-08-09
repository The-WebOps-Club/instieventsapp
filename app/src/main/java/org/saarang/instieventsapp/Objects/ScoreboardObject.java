package org.saarang.instieventsapp.Objects;

/**
 * Created by kiran on 8/8/15.
 */
public class ScoreboardObject {
     String hostelname,points,position;
    public ScoreboardObject(String hostelname,String points,String position){

        this.hostelname=hostelname;
        this.points=points;
        this.position=position;

    }
    public String getHostelname(){
        return hostelname;
    }
    public void setHostelname(String hostelname){
        this.hostelname=hostelname;
    }

    public String getPoints(){
        return points;
    }
    public void setPoints(String points){
        this.points=points;
    }

    public String getPosition(){
        return position;
    }
    public void setPosition(String position){
        this.position=position;
    }


}
