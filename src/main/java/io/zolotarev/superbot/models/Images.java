package io.zolotarev.superbot.models;

public class Images {
    private int id;
    private String telegramID;
    private String imageID;


    public Images(){
        this.id = id;
        this.telegramID = telegramID;
        this.imageID = imageID;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String gettelegramID(){
        return telegramID;
    }
    public void settelegramID(String telegramID){
        this.telegramID = telegramID;
    }

    public String getimageID(){
        return imageID;
    }
    public void setimageID(String imageID){
        this.imageID = imageID;
    }
}
