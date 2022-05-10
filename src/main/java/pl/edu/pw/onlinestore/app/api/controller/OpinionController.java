package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pw.onlinestore.app.api.dto.AddOpinion;
import pl.edu.pw.onlinestore.app.api.dto.AddProduct;
import pl.edu.pw.onlinestore.app.api.service.OpinionService;

@Controller
public class OpinionController {

    private OpinionService opinionService;

    public OpinionController(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @GetMapping("/add-opinion")
    public String getAddOpinionPage(Model model) {
        AddOpinion addOpinion = new AddOpinion();
        model.addAttribute("opinion", addOpinion);
        return "forms/add-opinion";
    }

    @PostMapping("/add-opinion")
    public String submitOpinion(@ModelAttribute("opinion") AddOpinion addOpinion, @RequestParam("profile") String username) {
        addOpinion.setReceiverUsername(username);
        opinionService.addOpinion(addOpinion);
        return "redirect:profile?username=" + username.toLowerCase();
    }

    @GetMapping("/opinion-edit")
    public String getEditOpinionPage() {
        return "forms/edit-opinion";
    }

}
