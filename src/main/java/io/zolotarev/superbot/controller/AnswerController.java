package io.zolotarev.superbot.controller;
import io.zolotarev.superbot.dao.AnswerDAO;
import org.springframework.ui.Model;
import io.zolotarev.superbot.dao.PollsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@Controller
public class AnswerController {

    private AnswerDAO answerDAO;

    @Autowired
    public AnswerController(AnswerDAO answerDAO){
        this.answerDAO = answerDAO;
    }


    @RequestMapping("/answers")
    public String answers(Model model) throws SQLException {
        model.addAttribute("answers" , answerDAO.getAllAnswer());
        return "answers";
    }
}
