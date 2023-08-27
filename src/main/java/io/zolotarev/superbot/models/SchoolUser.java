package io.zolotarev.superbot.models;

public class SchoolUser {
    private int id;
    private String Surname;
    private String Name_is;

    private String telegramID;
    private Integer Frame;
    private String Room;
    private String Centere;

    public SchoolUser(){
        this.Surname= Surname;
        this.Name_is = Name_is;
        this.telegramID = telegramID;
        this.Frame = Frame;
        this.Centere = Centere;
        this.Room = Room;

    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getSurname(){
        return Surname;
    }
    public void setASurname(String Surname){
        this.Surname = Surname;
    }

    public String getName_is(){
        return Name_is;
    }
    public void setName_is(String Name_is){
        this.Name_is = Name_is;
    }

    public String gettelegramID(){
        return telegramID;
    }
    public void settelegramID(String telegramID){
        this.telegramID = telegramID;
    }

    public Integer getFrame(){
        return Frame;
    }
    public void setFrame(Integer Frame){
        this.Frame = Frame;
    }

    public String getCentere(){
        return Centere;
    }
    public void setCentere(String Centere){
        this.Centere = Centere;
    }

    public String getRoom(){
        return Room;
    }
    public void setRoom(String Room){
        this.Room = Room;
    }
}
