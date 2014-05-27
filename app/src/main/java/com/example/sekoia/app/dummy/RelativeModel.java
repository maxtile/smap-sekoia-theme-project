package com.example.sekoia.app.dummy;

/**
 * Created by Rune on 27/05/14.
 */
public class RelativeModel {

    //int id;
    //String picPath;
    String firstName;
    String lastName;
    //String room;
    //String homeName;

    /*public RelativeModel(int id, String picPath, String firstName, String lastName, String room, String homeName){
        this.id = id;
        this.picPath = picPath;
        this.firstName = firstName;
        this.lastName = lastName;
        this.room = room;
        this.homeName = homeName;
    }*/

    public RelativeModel(String firstName, String lastName){
        super();
        this.firstName = firstName;
        this.lastName = lastName;

    }


    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }



    public String getTitle(){
        return firstName + " " + lastName;
    }


}
