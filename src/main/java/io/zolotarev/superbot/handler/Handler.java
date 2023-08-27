package io.zolotarev.superbot.handler;
import io.zolotarev.superbot.dao.PollsDAO;

import java.sql.SQLException;
import java.util.Objects;


public class Handler {

    private PollsDAO pollsDAO = new PollsDAO();

    private int state = 0;
    private int countQuestions = 0;
    private int next = 1;
    private int pollID = 1;

    private String userIdentificator = "";

    public void setPollId(int pID){
        pollID = pID;
    }

    public int getPollId(){
        return pollID;
    }

    public int getState(){
        return state;
    }

    public void setState(int number){
       state = number;
    }

    public String pollsInit() throws SQLException {
        countQuestions = pollsDAO.getAllQuestions(pollID).size();
        if(countQuestions > 0){
            state = 1;
        }

        if(state == 1){
            next = 1;
            return pollsDAO.getAllQuestions(pollID).get(0).getTitle();
        }

        return null;
    }


    public String nextQuestion() throws SQLException {
        if(state == 1 && next < countQuestions)
        {
            next = next+1;
            return pollsDAO.getAllQuestions(pollID).get((next-1)).getTitle();
        }
        else {
            state = 0;
            return "THE END";
        }

    }

    public void setUserId(String userId){
        if(Objects.equals(userIdentificator, "")){
            userIdentificator = userId;
        }
    }

    public  void saveAnswer (String answer) throws SQLException {
        if(state == 1){
            int pollNumber = getPollId();
            String question = pollsDAO.getAllQuestions(pollID).get((next-1)).getTitle();
            pollsDAO.saveAnswer(userIdentificator,question,answer,pollNumber);
        }
    }

    public int checkStateUser(String user) throws SQLException {
        int result = pollsDAO.checkuser(user, pollID);
        return result;
    }



}
