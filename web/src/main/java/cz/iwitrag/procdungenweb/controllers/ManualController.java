package cz.iwitrag.procdungenweb.controllers;

import cz.iwitrag.procdungen.api.ProcDunGenApi;
import cz.iwitrag.procdungen.api.configuration.DungeonConfiguration;
import cz.iwitrag.procdungen.api.data.Dungeon;
import cz.iwitrag.procdungenweb.Utils;
import cz.iwitrag.procdungenweb.model.DrawOptions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Page with form to enter configuration manually
 */
@Controller
public class ManualController {

    @GetMapping("/manual")
    public ModelAndView manual() {
        ModelAndView modelAndView = new ModelAndView("manual", "command", new DrawOptions());
        modelAndView.addObject("currentMenu", "manual");
        return modelAndView;
    }

    @PostMapping("/drawManual")
    public String drawFromManual(@ModelAttribute DrawOptions drawOptions, Model model) {
        model.addAttribute("currentMenu", "draw");
        Dungeon dungeon;
        try {
            dungeon = new ProcDunGenApi().generateDungeonPartially(DungeonConfiguration.fromString(drawOptions.getStringConfiguration(), drawOptions.getSupportedFileType()), drawOptions.getStopAfterPhase());
        } catch (Exception ex) {
            model.addAttribute("message", ex.getMessage());
            model.addAttribute("stack", Utils.getStackTrace(ex));
            return "error";
        }

        model.addAttribute("dungeon", dungeon);
        model.addAttribute("options", drawOptions);
        return "draw";
    }
}
