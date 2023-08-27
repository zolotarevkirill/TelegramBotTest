package io.zolotarev.superbot.controller;
import io.zolotarev.superbot.dao.ImagesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.SQLException;

@Controller
public class ImageController {

    private ImagesDAO imagesDAO;

    @Autowired
    public ImageController(ImagesDAO imagesDAO){
        this.imagesDAO = imagesDAO;
    }

    @RequestMapping("/images")
    public void images(Model model) throws SQLException {
        model.addAttribute("images" , imagesDAO.getImageList());
    }
}
