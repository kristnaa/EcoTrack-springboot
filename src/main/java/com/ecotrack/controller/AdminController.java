package com.ecotrack.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecotrack.model.User;
import com.ecotrack.service.EcoActionService;
import com.ecotrack.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private final EcoActionService ecoActionService;
	private final UserService userService;

	@GetMapping
	public String adminDashboard(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null || user.getRole() != User.Role.ADMIN) {
			return "redirect:/login";
		}

		// Add admin dashboard data
		return "admin";
	}

	@PostMapping("/action/verify/{id}")
	public String verifyAction(@PathVariable Long id) {
		ecoActionService.findById(id).ifPresent(action -> {
			action.setVerified(true);
			try {
				ecoActionService.addAction(action, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return "redirect:/admin";
	}
}