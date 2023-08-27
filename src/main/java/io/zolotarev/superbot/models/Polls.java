package io.zolotarev.superbot.models;

public class Polls {
    private int id;
    private String title;
    private int number; 

    public Polls(){
        this.id = id;
        this.title = title;
        this.number = number;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getNumber(){
        return this.number;
    }

    public void setNumber(int number){
        this.number = number;
    }

}
