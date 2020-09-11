package cz.iwitrag.procdungenweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Homepage
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("currentMenu", "index");
        return "index";
    }

}
