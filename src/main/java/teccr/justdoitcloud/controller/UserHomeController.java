package teccr.justdoitcloud.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import teccr.justdoitcloud.data.User;

@Controller
public class UserHomeController {
    @GetMapping("/user/home")
    public String showUserHome(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "userhome";
    }

}
