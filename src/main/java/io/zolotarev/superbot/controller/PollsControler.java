package io.zolotarev.superbot.controller;
import org.springframework.ui.Model;
import io.zolotarev.superbot.dao.PollsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequestMapping("/polls")
public class PollsControler {

    private PollsDAO pollsDAO;


    @Autowired
    public PollsControler(PollsDAO pollsDAO){
        this.pollsDAO = pollsDAO;
    }


    @GetMapping()
    public void questions(Model model,  @RequestParam(defaultValue = "1") int category) throws SQLException {
        model.addAttribute("polls", PollsDAO.getAllPolls(category));
    }

    @GetMapping("/add")
    public void addPolls(Model model, @RequestParam int category_id, @RequestParam String category_title) throws SQLException {
        PollsDAO.addPolls(category_id, category_title);
        model.addAttribute("add");
    }

    @GetMapping("/delete")
    public void deletePolls(Model model, @RequestParam int id) throws SQLException {
        PollsDAO.deletePolls(id);
        model.addAttribute("delete");
    }



}
