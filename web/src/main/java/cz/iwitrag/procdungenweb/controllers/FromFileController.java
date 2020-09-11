package cz.iwitrag.procdungenweb.controllers;

import cz.iwitrag.procdungen.api.ProcDunGenApi;
import cz.iwitrag.procdungen.api.configuration.DungeonConfiguration;
import cz.iwitrag.procdungen.api.data.Dungeon;
import cz.iwitrag.procdungen.util.SupportedFileType;
import cz.iwitrag.procdungenweb.Utils;
import cz.iwitrag.procdungenweb.model.DrawOptions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Page with form to upload file with configuration
 */
@Controller
public class FromFileController {

    @GetMapping("/fromFile")
    public ModelAndView fromFile(Model model) {
        ModelAndView modelAndView = new ModelAndView("from_file", "command", new DrawOptions());
        modelAndView.addObject("currentMenu", "fromFile");
        return modelAndView;
    }

    @PostMapping("/drawFromFile")
    public String drawFromFile(@ModelAttribute DrawOptions drawOptions, Model model) {
        model.addAttribute("currentMenu", "draw");
        String configuration;
        try {
            configuration = new String(drawOptions.getFileConfiguration().getBytes());
        } catch (IOException ex) {
            model.addAttribute("message", ex.getMessage());
            model.addAttribute("stack", Utils.getStackTrace(ex));
            return "error";
        }
        Dungeon dungeon;
        try {
            SupportedFileType supportedFileType = SupportedFileType.fromFileName(drawOptions.getFileConfiguration().getOriginalFilename());
            if (supportedFileType == null)
                throw new IOException("This file type is not supported");
            dungeon = new ProcDunGenApi().generateDungeonPartially(DungeonConfiguration.fromString(configuration, supportedFileType), drawOptions.getStopAfterPhase());
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
