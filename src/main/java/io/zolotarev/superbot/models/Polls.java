package io.zolotarev.superbot.models;

public class Polls {
    private int id;
    private String title;

    public Polls(){
        this.id = id;
        this.title = title;
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

}
