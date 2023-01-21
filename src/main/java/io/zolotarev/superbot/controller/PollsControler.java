package io.zolotarev.superbot.controller;
import org.springframework.ui.Model;
import io.zolotarev.superbot.dao.PollsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@Controller
public class PollsControler {

    private PollsDAO pollsDAO;


    @Autowired
    public PollsControler(PollsDAO pollsDAO){
        this.pollsDAO = pollsDAO;
    }


    @RequestMapping("/questions")
    public String questions(Model model) throws SQLException {
        model.addAttribute("questions" , pollsDAO.getAllQuestions());
        return "questions";
    }

    @RequestMapping("/delete")
    public String deleteQuestions(Model model) throws SQLException {
        pollsDAO.deleteQuestions();
        return "delete";
    }

    @RequestMapping("/add")
    public String addQuestions(Model model) throws SQLException {
        return "add";
    }
}
