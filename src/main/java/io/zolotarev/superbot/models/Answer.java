package io.zolotarev.superbot.models;

public class Answer {

    private String id;

    private String question;

    private String answer;

    public Answer(){
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getAnswer(){
        return answer;
    }
    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getQuestion(){
        return question;
    }
    public void setQuestion(String question){
        this.question = question;
    }
}
