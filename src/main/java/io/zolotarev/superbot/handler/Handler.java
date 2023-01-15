package io.zolotarev.superbot.handler;

import io.zolotarev.superbot.models.Question;

import java.util.ArrayList;

public class Handler {

    private int state = 0;
    private int next = 0;


    public int getState(){
        return state;
    }

    public void setState(int number){
       state = number;
    }

    public String pollsInit(){
        state = 1;

        if(state == 1){
            return "Начинается опрос";
        }

        return null;
    }


    public String nextQuestion(){
        if(state == 1)
        {
            return "Вопрос 2";
        }

        return null;
    }




}
