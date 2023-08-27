package io.zolotarev.superbot.models;

import org.springframework.boot.context.properties.ConstructorBinding;

public class Players {
    private int id;
    private String name;
    private String code1;
    private String code2;
    private String code3;
    private String code4;
    private String code5;
    private String code6;
    private int user_id;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String img6;

    public Players(){
        this.id = id;
        this.name = name;
        this.code1 = code1;
        this.code2 = code2;
        this.code3 = code3;
        this.code4 = code4;
        this.code5 = code5;
        this.code6 = code6;
        this.user_id = user_id;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
        this.img6 = img6;
    }

    public String getImg1(){
        return img1;
    }
    public void setImg1(String img1){
        this.img1 = img1;
    }

    public String getImg2(){
        return img2;
    }
    public void setImg2(String img2){
        this.img2 = img2;
    }

    public String getImg3(){
        return img3;
    }
    public void setImg3(String img3){
        this.img3 = img3;
    }

    public String getImg4(){
        return img4;
    }
    public void setImg4(String img4){
        this.img4 = img4;
    }

    public String getImg5(){
        return img5;
    }
    public void setImg5(String img5){
        this.img5 = img5;
    }

    public String getImg6(){
        return img6;
    }
    public void setImg6(String img6){
        this.img6 = img6;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public int getUserID(){
        return user_id;
    }
    public void setUserID(int user_id){
        this.user_id = user_id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getCode1(){
        return code1;
    }
    public void setCode1(String code1){
        this.code1 = code1;
    }

    public String getCode2(){
        return code2;
    }
    public void setCode2(String code2){
        this.code2 = code2;
    }

    public String getCode3(){
        return code3;
    }
    public void setCode3(String code3){
        this.code3 = code3;
    }

    public String getCode4(){
        return code4;
    }
    public void setCode4(String code4){
        this.code4 = code4;
    }

    public String getCode5(){
        return code5;
    }
    public void setCode5(String code5){
        this.code5 = code5;
    }

    public String getCode6(){
        return code6;
    }
    public void setCode6(String code6){
        this.code6 = code6;
    }

}
