package cz.iwitrag.procdungenweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Page with information about website and its purpose
 */
@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("currentMenu", "about");
        return "about";
    }

}
