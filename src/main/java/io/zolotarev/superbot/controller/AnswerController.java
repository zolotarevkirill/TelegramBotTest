package io.zolotarev.superbot.controller;
import io.zolotarev.superbot.dao.AnswerDAO;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class AnswerController {

    private AnswerDAO answerDAO;

    @Autowired
    public AnswerController(AnswerDAO answerDAO){
        this.answerDAO = answerDAO;
    }

    @GetMapping("/admin")
    public void answers(Model model){
        model.addAttribute("admin");
    }

    @GetMapping("/answers")
    public void answers(Model model,  @RequestParam(defaultValue = "1") int category) throws SQLException {
        model.addAttribute("answers", AnswerDAO.getAnswers(category));
    }
}
