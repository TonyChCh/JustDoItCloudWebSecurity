package teccr.justdoitcloud.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import teccr.justdoitcloud.data.Task;
import teccr.justdoitcloud.data.User;
import teccr.justdoitcloud.service.UserService;

@Slf4j
@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public String showAdminManaging(Model model, @AuthenticationPrincipal User user) {
        User newuser = new User();
        model.addAttribute("newUser", newuser);
        return "admin";
    }

    @PostMapping("/createuser")
    public String createUser(@Valid @ModelAttribute(name = "newUser") User newUser,
                            Errors errors,
                            @AuthenticationPrincipal User user) {
        log.info("Adding user: " + newUser);
        if (errors.hasErrors()) {
            return "admin";
        }

        userService.createUser(newUser);
        return "redirect:/admin";
    }
}
