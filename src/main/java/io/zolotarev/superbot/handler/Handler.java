package io.zolotarev.superbot.handler;
import io.zolotarev.superbot.dao.PollsDAO;

import java.sql.SQLException;


public class Handler {

    private PollsDAO pollsDAO = new PollsDAO();

    private int state = 0;
    private int countQuestions = 0;
    private int next = 1;


    public int getState(){
        return state;
    }

    public void setState(int number){
       state = number;
    }

    public String pollsInit() throws SQLException {
        System.out.println("*******");
        System.out.println(pollsDAO.getAllQuestions().get(0));
        System.out.println("*********");
        countQuestions = pollsDAO.getAllQuestions().size();
        if(countQuestions > 0){
            state = 1;
        }

        if(state == 1){
            next = 1;
            return pollsDAO.getAllQuestions().get(0).getTitle();
        }

        return null;
    }


    public String nextQuestion() throws SQLException {
        if(state == 1 && next < countQuestions)
        {
            next = next+1;
            return pollsDAO.getAllQuestions().get((next-1)).getTitle();
        }
        else {
            state = 0;
            return "Опрос окончен";
        }

    }




}
