package com.ecotrack.controller;

import com.ecotrack.model.EcoAction;
import com.ecotrack.model.User;
import com.ecotrack.service.EcoActionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class EcoActionController {
    private final EcoActionService ecoActionService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        model.addAttribute("actions", ecoActionService.getUserActions(user));
        return "dashboard";
    }

    @GetMapping("/action/add")
    public String showAddActionForm(Model model) {
        model.addAttribute("ecoAction", new EcoAction());
        return "add-action";
    }

    @PostMapping("/action/add")
    public String addAction(@Valid @ModelAttribute EcoAction action, 
                          BindingResult result,
                          @RequestParam(required = false) MultipartFile image,
                          HttpSession session) throws IOException {
        if (result.hasErrors()) {
            return "add-action";
        }
        
        User user = (User) session.getAttribute("user");
        action.setUser(user);
        ecoActionService.addAction(action, image);
        return "redirect:/dashboard";
    }

    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {
        model.addAttribute("leaders", ecoActionService.getLeaderboard());
        return "leaderboard";
    }

    @GetMapping("/action/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ecoActionService.findById(id).ifPresent(action -> model.addAttribute("ecoAction", action));
        return "edit-action";
    }

    @PostMapping("/action/delete/{id}")
    public String deleteAction(@PathVariable Long id) {
        ecoActionService.deleteAction(id);
        return "redirect:/dashboard";
    }
}