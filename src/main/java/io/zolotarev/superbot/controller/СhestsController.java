package io.zolotarev.superbot.controller;
import io.zolotarev.superbot.dao.PlayerDAO;
import io.zolotarev.superbot.models.Players;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.google.zxing.WriterException;

import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.POST;

@Controller
@RequestMapping("/play")
public class СhestsController {


    private PlayerDAO playerDAO;

    @Autowired
    public СhestsController(PlayerDAO playerDAO){
        this.playerDAO = playerDAO;
    }

    @GetMapping()
    public void players(Model model) {
        try {
            model.addAttribute("players" , playerDAO.getPlayers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/form")
    public void addPlayer(Model model) throws SQLException {
        model.addAttribute("form");
    }

    @GetMapping("/succes")
    public void succes(Model model, @RequestParam String name) throws SQLException, WriterException, IOException {
        PlayerDAO.createPlayer(name);
    }

    @GetMapping("/delete")
    public void delete(Model model, @RequestParam String id) throws SQLException {
        PlayerDAO.deletePlayer(id);
    }

    @GetMapping("/open")
    public void open(Model model, @RequestParam String id) throws SQLException {
        System.out.println("!!!!!!!");
        System.out.println(id);
        System.out.println("!!!!!!!");
        model.addAttribute("open");
    }

    /**
     * @param model
     * @param param
     * @throws SQLException
     */
    @PostMapping("/open")
    public RedirectView openPost(Model model, @RequestBody String param) throws SQLException{
        String str = param;
        String delimeter = "&";
        String[] subStr;
        System.out.println(param); 
        subStr = str.split(delimeter);
        String n = "";
        String code = "";
        String id = "";
        for(int i = 0; i < subStr.length; i++) {
            if(i == 0){
                n = subStr[i].replaceAll("n=", "");
            } 
            if(i == 1){
                code = subStr[i].replaceAll("code=", "");
            }
            if(i == 2){
                id = subStr[i].replaceAll("id=", "");
            }
        }
        System.out.println("N = "+n); 
        System.out.println("Code = "+code); 
        System.out.println("ID = "+id); 
        boolean check = PlayerDAO.checkCode(n,code,id);
        System.out.println("check");
        System.out.println(check);
        System.out.println("check");
        if(check){
            return new RedirectView("/play");
        }else{
            System.out.println("ELSE");
            return new RedirectView("/play/codesnotucces");
        }
    }

    @GetMapping("/codesnotucces")
    public void open(Model model) throws SQLException {
        model.addAttribute("codesnotucces");
    }

    @GetMapping("/admin")
    public void admin(Model model) throws SQLException {
        model.addAttribute("players", playerDAO.getPlayers());
    }
}