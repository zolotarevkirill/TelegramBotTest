package io.zolotarev.superbot.controller;

import org.springframework.stereotype.Controller;

@Controller
public class PollsControler {

    public String getPolls(){
        //Контролер админки. Список опросов.
        return "LIST";
    }
}
