package com.example.sekoia.app.models;

/**
 * Created by Rune on 27/05/14.
 */
public class Relative {

    int id;
    int picPath;
    String firstName;
    String lastName;
    String room;
    String homeName;

    public Relative(int id, String firstName, String lastName, int picPath, String homeName, String room){
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picPath = picPath;
        this.homeName = homeName;
        this.room = room;
    }


    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public int getPicPath(){
        return picPath;
    }
    public String getHomeName(){
        return homeName;
    }
    public String getRoom(){
        return room;
    }
    public int getId(){
        return id;
    }



}
