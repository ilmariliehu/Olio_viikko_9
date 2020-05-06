package com.example.week9_1;

public class Theatres {
    String id;
    String name;

    public void setInfo(String n, String i){
        name = n;
        id = i;
    }
    public String getName(){
        return name;
    }
    public String getID(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setId(String id){
        this.id = id;
    }
}
