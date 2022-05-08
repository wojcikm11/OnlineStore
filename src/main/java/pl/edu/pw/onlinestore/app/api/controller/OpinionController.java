package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpinionController {

    @GetMapping("/add-opinion")
    public String getAddOpinionPage() {
        return "forms/add-opinion";
    }

    @GetMapping("/opinion-edit")
    public String getEditOpinionPage() {
        return "forms/edit-opinion";
    }

}
